package io.github.whdt.wldt.plugin.factory

import io.github.whdt.core.hdt.HumanDigitalTwin
import io.github.whdt.core.hdt.interfaces.digital.HttpDigitalInterface
import io.github.whdt.core.hdt.interfaces.digital.MqttDigitalInterface
import io.github.whdt.core.hdt.interfaces.physical.MqttPhysicalInterface
import io.github.whdt.core.hdt.model.property.Property
import io.github.whdt.core.hdt.storage.StorageType
import io.github.whdt.distributed.namespace.Namespace
import io.github.whdt.distributed.serde.Stub
import io.github.whdt.wldt.plugin.shadowing.HdtShadowingFunction
import it.wldt.adapter.digital.DigitalAdapter
import it.wldt.adapter.http.digital.adapter.HttpDigitalAdapter
import it.wldt.adapter.http.digital.adapter.HttpDigitalAdapterConfiguration
import it.wldt.adapter.mqtt.digital.MqttDigitalAdapter
import it.wldt.adapter.mqtt.digital.MqttDigitalAdapterConfiguration
import it.wldt.adapter.mqtt.digital.topic.MqttQosLevel
import it.wldt.adapter.mqtt.physical.MqttPhysicalAdapter
import it.wldt.adapter.mqtt.physical.MqttPhysicalAdapterConfiguration
import it.wldt.adapter.physical.PhysicalAdapter
import it.wldt.core.engine.DigitalTwin
import it.wldt.storage.DefaultWldtStorage
import java.util.logging.Logger
import kotlin.time.ExperimentalTime

object HumanDigitalTwinFactory {
    val logger: Logger = Logger.getLogger("HumanDigitalTwinFactory")
    val propertySerDe = Stub.propertyJsonSerDe()
    //val messageSerDe = Stub.messageJsonSerDe()
    fun fromHumanDigitalTwin(hdt: HumanDigitalTwin): DigitalTwin {

        val shad = HdtShadowingFunction("${hdt.hdtId}-shadowing-function", hdt.models)
        val dt = DigitalTwin(hdt.hdtId.id, shad)

        val properties = hdt.models.flatMap { it.properties }

        hdt.physicalInterfaces.forEach {
            val pI: PhysicalAdapter? = when (it) {
                is MqttPhysicalInterface -> getPaFromPhysicalInterfaceMqtt(it, properties)
                // Handle other physical interfaces if needed
                else -> {
                    logger.warning("cannot handle physical interface of type ${it.javaClass}")
                    null
                }
            }
            if(pI != null) {
                dt.addPhysicalAdapter(pI)
            }
        }

        hdt.digitalInterfaces.forEach {
            val dI: DigitalAdapter<*>? = when (it) {
                is MqttDigitalInterface -> getDaFromDigitalInterfaceMqtt(it, properties)
                is HttpDigitalInterface -> getDaFromHttpDigitalInterface(it, dt, properties)
                else -> {
                    logger.warning("cannot handle digital interface of type ${it.javaClass}")
                    null
                }
            }
            if(dI != null) {
                dt.addDigitalAdapter(dI)
            }
        }


        val storages = hdt.storages.map { storage ->
            when(storage.storageType) {
                StorageType.IN_MEMORY -> DefaultWldtStorage("${hdt.hdtId}-default-storage", true)
                else -> {
                    DefaultWldtStorage("${hdt.hdtId}-default-storage", true)
                }
            }
        }

        storages.forEach { dt.storageManager.putStorage(it) }

        return dt
    }

    fun getPaFromPhysicalInterfaceMqtt(pI: MqttPhysicalInterface, properties: List<Property>): MqttPhysicalAdapter {
        val mqttConfigBuilder = MqttPhysicalAdapterConfiguration.builder(
            pI.broker,
            pI.port,
        )

        properties.forEach { property ->
            mqttConfigBuilder.addPhysicalAssetPropertyAndTopic(
                property.id,
                property,
                Namespace.propertyUpdateRequestTopic(pI.hdtId, property.id)
            ) { string ->
                //val message = messageSerDe.deserialize(string)
                val property = propertySerDe.deserialize(string)
                property
            }
        }

        val mqttConfig = mqttConfigBuilder.build()

        return MqttPhysicalAdapter(
            pI.id,
            mqttConfig
        )
    }

    @OptIn(ExperimentalTime::class)
    fun getDaFromDigitalInterfaceMqtt(dI: MqttDigitalInterface, properties: List<Property>): MqttDigitalAdapter {
        val mqttConfigBuilder = MqttDigitalAdapterConfiguration.builder(
            dI.broker,
            dI.port,
        )

        properties.forEach { property ->
            mqttConfigBuilder.addPropertyTopic(
                property.id,
                Namespace.propertyUpdateNotificationTopic(dI.hdtId, property.id),
                MqttQosLevel.MQTT_QOS_0
            ) { property: Property ->
                // Build a Message
                /*val message = Message(
                    hdt = dI.hdtId,
                    sender = SenderId.of(dI.id),
                    sentAt = Clock.System.now().toEpochMilliseconds(),
                    payload = propertySerDe.serializeToJsonElement(property)
                )*/
                propertySerDe.serialize(property)
            }
        }

        val mqttConfig = mqttConfigBuilder.build()

        return MqttDigitalAdapter(
            dI.id,
            mqttConfig
        )
    }

    fun getDaFromHttpDigitalInterface(dI: HttpDigitalInterface, dt: DigitalTwin, properties: List<Property>): HttpDigitalAdapter {
        val httpConfig  = HttpDigitalAdapterConfiguration(dI.id, dI.host, dI.port)

        httpConfig.addPropertiesFilter(properties.map { it.id })

        return HttpDigitalAdapter(
            httpConfig,
            dt
        )
    }
}