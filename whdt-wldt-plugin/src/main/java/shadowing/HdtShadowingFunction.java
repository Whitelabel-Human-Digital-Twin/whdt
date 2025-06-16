package shadowing;

import hdt.Model;
import hdt.model.Property;
import it.wldt.adapter.digital.event.DigitalActionWldtEvent;
import it.wldt.adapter.physical.PhysicalAssetDescription;
import it.wldt.adapter.physical.event.PhysicalAssetEventWldtEvent;
import it.wldt.adapter.physical.event.PhysicalAssetPropertyWldtEvent;
import it.wldt.adapter.physical.event.PhysicalAssetRelationshipInstanceCreatedWldtEvent;
import it.wldt.adapter.physical.event.PhysicalAssetRelationshipInstanceDeletedWldtEvent;
import it.wldt.core.model.ShadowingFunction;
import it.wldt.core.state.DigitalTwinStateAction;
import it.wldt.core.state.DigitalTwinStateEvent;
import it.wldt.core.state.DigitalTwinStateEventNotification;
import it.wldt.core.state.DigitalTwinStateProperty;
import it.wldt.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HdtShadowingFunction extends ShadowingFunction {
    private static final Logger logger = LoggerFactory.getLogger(HdtShadowingFunction.class);
    private final List<Model> models;

    public HdtShadowingFunction(String id, List<Model> models) {
        super(id);
        this.models = models;
    }

    @Override
    protected void onCreate() {
        logger.debug("Shadowing - OnCreate");
        setupStartingModels();
    }

    @Override
    protected void onStart() {
        logger.debug("Shadowing - OnStart");
    }

    @Override
    protected void onStop() {
        logger.debug("Shadowing - OnStop");
    }

    @Override
    protected void onDigitalTwinBound(Map<String, PhysicalAssetDescription> adaptersPhysicalAssetDescriptionMap) {
        logger.debug("Shadowing - onDtBound");

        try {

            startShadowing(adaptersPhysicalAssetDescriptionMap);

            // Observe all available properties
            this.observePhysicalAssetProperties(adaptersPhysicalAssetDescriptionMap.values()
                    .stream()
                    .flatMap(pad -> pad.getProperties().stream())
                    .collect(Collectors.toList()));

            // observes all the available events
            this.observePhysicalAssetEvents(adaptersPhysicalAssetDescriptionMap.values()
                    .stream()
                    .flatMap(pad -> pad.getEvents().stream())
                    .collect(Collectors.toList()));

            this.observeDigitalActionEvents();

        } catch (EventBusException | ModelException | WldtDigitalTwinStateException e) {
            logger.error("Error during digital twin binding", e);
        }
    }

    @Override
    protected void onDigitalTwinUnBound(Map<String, PhysicalAssetDescription> map, String s) {
        logger.debug("Shadowing - onDtUnBound");
    }

    @Override
    protected void onPhysicalAdapterBidingUpdate(String adapterId, PhysicalAssetDescription adapterPhysicalAssetDescription) {
        logger.info("Shadowing - onPABindingUpdate - updated Adapter: {}, new PAD: {}", adapterId,
                adapterPhysicalAssetDescription);
    }

    @Override
    protected void onPhysicalAssetPropertyVariation(PhysicalAssetPropertyWldtEvent<?> physicalPropertyEventMessage) {
        logger.info("Shadowing - onPAPropertyVariation - property event: {} ", physicalPropertyEventMessage);
        // Update Digital Twin Status
        try {
            this.digitalTwinStateManager.startStateTransaction();

            this.digitalTwinStateManager.updateProperty(
                    new DigitalTwinStateProperty<>(
                            physicalPropertyEventMessage.getPhysicalPropertyId(),
                            physicalPropertyEventMessage.getBody()));

            this.digitalTwinStateManager.commitStateTransaction();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    protected void onPhysicalAssetEventNotification(PhysicalAssetEventWldtEvent<?> physicalAssetEventWldtEvent) {
        logger.info("Shadowing - onPhysicalAssetEventNotification - received Event:{}", physicalAssetEventWldtEvent);
        try {
            this.digitalTwinStateManager.notifyDigitalTwinStateEvent(new DigitalTwinStateEventNotification<>(
                    physicalAssetEventWldtEvent.getPhysicalEventKey(),
                    (String) physicalAssetEventWldtEvent.getBody(),
                    System.currentTimeMillis()));
        } catch (WldtDigitalTwinStateEventNotificationException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    protected void onPhysicalAssetRelationshipEstablished(PhysicalAssetRelationshipInstanceCreatedWldtEvent<?> physicalAssetRelationshipInstanceCreatedWldtEvent) {

    }

    @Override
    protected void onPhysicalAssetRelationshipDeleted(PhysicalAssetRelationshipInstanceDeletedWldtEvent<?> physicalAssetRelationshipInstanceDeletedWldtEvent) {

    }

    @Override
    protected void onDigitalActionEvent(DigitalActionWldtEvent<?> digitalActionWldtEvent) {
        logger.info("Shadowing - onDigitalActionEvent - received:{}", digitalActionWldtEvent);
        try {
            publishPhysicalAssetActionWldtEvent(digitalActionWldtEvent.getActionKey(),
                    digitalActionWldtEvent.getBody());
        } catch (EventBusException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void setupStartingModels() {

        logger.debug("Setting up models for shadowing");

        this.models.forEach(m -> {
            try {
                this.digitalTwinStateManager.startStateTransaction();
                m.getProperties().forEach(p -> {
                    try {
                        this.digitalTwinStateManager.createProperty(new DigitalTwinStateProperty<>(p.getInternalName(), p));
                    } catch (WldtDigitalTwinStateException e) {
                        throw new RuntimeException(e);
                    }
                });
                this.digitalTwinStateManager.commitStateTransaction();
            } catch (Exception e) {
                logger.error("Error setting up model", e);
            }
        });
    }

    private void startShadowing(Map<String, PhysicalAssetDescription> adaptersPhysicalAssetDescriptionMap)
            throws WldtDigitalTwinStateException {

        this.digitalTwinStateManager.startStateTransaction();

        adaptersPhysicalAssetDescriptionMap.forEach((id, pad) -> {
            pad.getProperties()
                    .forEach(p -> {
                        try {
                            if(! this.digitalTwinStateManager.getDigitalTwinState().containsProperty(p.getKey())) {
                                this.digitalTwinStateManager
                                        .createProperty(new DigitalTwinStateProperty<>(p.getKey(), p.getInitialValue()));
                            }
                        } catch (WldtDigitalTwinStateException | WldtDigitalTwinStatePropertyException e) {
                            logger.error("Error creating property for PAD: {}", id, e);
                        }
                    });
            pad.getActions().forEach(a -> {
                try {
                    if(!this.digitalTwinStateManager.getDigitalTwinState().containsAction(a.getKey())) {
                        this.digitalTwinStateManager
                                .enableAction(new DigitalTwinStateAction(a.getKey(), a.getType(), a.getContentType()));
                    }
                } catch (WldtDigitalTwinStateException | WldtDigitalTwinStateActionException e) {
                    logger.error("Error enabling action for PAD: {}", id, e);
                }
            });
            pad.getEvents().forEach(e -> {
                try {
                    if(! this.digitalTwinStateManager.getDigitalTwinState().containsEvent(e.getKey())) {
                        this.digitalTwinStateManager.registerEvent(new DigitalTwinStateEvent(e.getKey(), e.getType()));
                    }
                } catch (WldtDigitalTwinStateException |
                         WldtDigitalTwinStateEventException ex) {
                    logger.error("Error registering event for PAD: {}", id, ex);
                }
            });
        });

        this.digitalTwinStateManager.commitStateTransaction();

        notifyShadowingSync();
    }
}
