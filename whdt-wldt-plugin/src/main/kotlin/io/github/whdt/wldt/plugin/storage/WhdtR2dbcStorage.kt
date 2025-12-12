package io.github.whdt.wldt.plugin.storage

import it.wldt.adapter.digital.DigitalActionRequest
import it.wldt.adapter.physical.PhysicalAssetActionRequest
import it.wldt.adapter.physical.PhysicalAssetDescriptionNotification
import it.wldt.adapter.physical.PhysicalAssetEventNotification
import it.wldt.adapter.physical.PhysicalAssetPropertyVariation
import it.wldt.adapter.physical.PhysicalRelationshipInstanceVariation
import it.wldt.core.engine.LifeCycleStateVariation
import it.wldt.core.state.DigitalTwinState
import it.wldt.core.state.DigitalTwinStateChange
import it.wldt.core.state.DigitalTwinStateEventNotification
import it.wldt.storage.WldtStorage
import it.wldt.storage.model.StorageStats
import it.wldt.storage.model.digital.DigitalActionRequestRecord
import it.wldt.storage.model.lifecycle.LifeCycleVariationRecord
import it.wldt.storage.model.physical.PhysicalAssetActionRequestRecord
import it.wldt.storage.model.physical.PhysicalAssetDescriptionNotificationRecord
import it.wldt.storage.model.physical.PhysicalAssetEventNotificationRecord
import it.wldt.storage.model.physical.PhysicalAssetPropertyVariationRecord
import it.wldt.storage.model.physical.PhysicalRelationshipInstanceVariationRecord
import it.wldt.storage.model.state.DigitalTwinStateEventNotificationRecord
import it.wldt.storage.model.state.DigitalTwinStateRecord
import java.util.Optional

class WhdtR2dbcStorage(storageId: String, observeAll: Boolean = false) : WldtStorage(storageId, observeAll) {

    override fun saveDigitalTwinState(
        digitalTwinState: DigitalTwinState?,
        digitalTwinStateChangeList: List<DigitalTwinStateChange?>?
    ) {
        TODO("Not yet implemented")
    }

    override fun getLastDigitalTwinState(): Optional<DigitalTwinStateRecord?>? {
        TODO("Not yet implemented")
    }

    override fun getDigitalTwinStateCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getDigitalTwinStateInTimeRange(
        startTimestampMs: Long,
        endTimestampMs: Long
    ): List<DigitalTwinStateRecord?>? {
        TODO("Not yet implemented")
    }

    override fun getDigitalTwinStateInRange(
        startIndex: Int,
        endIndex: Int
    ): List<DigitalTwinStateRecord?>? {
        TODO("Not yet implemented")
    }

    override fun saveDigitalTwinStateEventNotification(digitalTwinStateEventNotification: DigitalTwinStateEventNotification<*>?) {
        TODO("Not yet implemented")
    }

    override fun getDigitalTwinStateEventNotificationCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getDigitalTwinStateEventNotificationInTimeRange(
        startTimestampMs: Long,
        endTimestampMs: Long
    ): List<DigitalTwinStateEventNotificationRecord?>? {
        TODO("Not yet implemented")
    }

    override fun getDigitalTwinStateEventNotificationInRange(
        startIndex: Int,
        endIndex: Int
    ): List<DigitalTwinStateEventNotificationRecord?>? {
        TODO("Not yet implemented")
    }

    override fun saveLifeCycleState(lifeCycleStateVariation: LifeCycleStateVariation?) {
        TODO("Not yet implemented")
    }

    override fun getLastLifeCycleState(): LifeCycleVariationRecord? {
        TODO("Not yet implemented")
    }

    override fun getLifeCycleStateCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getLifeCycleStateInTimeRange(
        startTimestampMs: Long,
        endTimestampMs: Long
    ): List<LifeCycleVariationRecord?>? {
        TODO("Not yet implemented")
    }

    override fun getLifeCycleStateInRange(
        startIndex: Int,
        endIndex: Int
    ): List<LifeCycleVariationRecord?>? {
        TODO("Not yet implemented")
    }

    override fun savePhysicalAssetEventNotification(physicalAssetEventNotification: PhysicalAssetEventNotification?) {
        TODO("Not yet implemented")
    }

    override fun getPhysicalAssetEventNotificationCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getPhysicalAssetEventNotificationInTimeRange(
        startTimestampMs: Long,
        endTimestampMs: Long
    ): List<PhysicalAssetEventNotificationRecord?>? {
        TODO("Not yet implemented")
    }

    override fun getPhysicalAssetEventNotificationInRange(
        startIndex: Int,
        endIndex: Int
    ): List<PhysicalAssetEventNotificationRecord?>? {
        TODO("Not yet implemented")
    }

    override fun savePhysicalAssetActionRequest(physicalAssetActionRequest: PhysicalAssetActionRequest?) {
        TODO("Not yet implemented")
    }

    override fun getPhysicalAssetActionRequestCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getPhysicalAssetActionRequestInTimeRange(
        startTimestampMs: Long,
        endTimestampMs: Long
    ): List<PhysicalAssetActionRequestRecord?>? {
        TODO("Not yet implemented")
    }

    override fun getPhysicalAssetActionRequestInRange(
        startIndex: Int,
        endIndex: Int
    ): List<PhysicalAssetActionRequestRecord?>? {
        TODO("Not yet implemented")
    }

    override fun saveDigitalActionRequest(digitalActionRequest: DigitalActionRequest?) {
        TODO("Not yet implemented")
    }

    override fun getDigitalActionRequestCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getDigitalActionRequestInTimeRange(
        startTimestampMs: Long,
        endTimestampMs: Long
    ): List<DigitalActionRequestRecord?>? {
        TODO("Not yet implemented")
    }

    override fun getDigitalActionRequestInRange(
        startIndex: Int,
        endIndex: Int
    ): List<DigitalActionRequestRecord?>? {
        TODO("Not yet implemented")
    }

    override fun saveNewPhysicalAssetDescriptionNotification(physicalAssetDescriptionNotification: PhysicalAssetDescriptionNotification?) {
        TODO("Not yet implemented")
    }

    override fun getNewPhysicalAssetDescriptionNotificationCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getNewPhysicalAssetDescriptionNotificationInTimeRange(
        startTimestampMs: Long,
        endTimestampMs: Long
    ): List<PhysicalAssetDescriptionNotificationRecord?>? {
        TODO("Not yet implemented")
    }

    override fun getNewPhysicalAssetDescriptionNotificationInRange(
        startIndex: Int,
        endIndex: Int
    ): List<PhysicalAssetDescriptionNotificationRecord?>? {
        TODO("Not yet implemented")
    }

    override fun saveUpdatedPhysicalAssetDescriptionNotification(physicalAssetDescriptionNotification: PhysicalAssetDescriptionNotification?) {
        TODO("Not yet implemented")
    }

    override fun getUpdatedPhysicalAssetDescriptionNotificationCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getUpdatedPhysicalAssetDescriptionNotificationInTimeRange(
        startTimestampMs: Long,
        endTimestampMs: Long
    ): List<PhysicalAssetDescriptionNotificationRecord?>? {
        TODO("Not yet implemented")
    }

    override fun getUpdatedPhysicalAssetDescriptionNotificationInRange(
        startIndex: Int,
        endIndex: Int
    ): List<PhysicalAssetDescriptionNotificationRecord?>? {
        TODO("Not yet implemented")
    }

    override fun savePhysicalAssetPropertyVariation(physicalAssetPropertyVariation: PhysicalAssetPropertyVariation?) {
        TODO("Not yet implemented")
    }

    override fun getPhysicalAssetPropertyVariationCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getPhysicalAssetPropertyVariationInTimeRange(
        startTimestampMs: Long,
        endTimestampMs: Long
    ): List<PhysicalAssetPropertyVariationRecord?>? {
        TODO("Not yet implemented")
    }

    override fun getPhysicalAssetPropertyVariationInRange(
        startIndex: Int,
        endIndex: Int
    ): List<PhysicalAssetPropertyVariationRecord?>? {
        TODO("Not yet implemented")
    }

    override fun savePhysicalAssetRelationshipInstanceCreatedNotification(physicalRelationshipInstanceVariation: PhysicalRelationshipInstanceVariation?) {
        TODO("Not yet implemented")
    }

    override fun getPhysicalAssetRelationshipInstanceCreatedNotificationCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getPhysicalAssetRelationshipInstanceCreatedNotificationInTimeRange(
        startTimestampMs: Long,
        endTimestampMs: Long
    ): List<PhysicalRelationshipInstanceVariationRecord?>? {
        TODO("Not yet implemented")
    }

    override fun getPhysicalAssetRelationshipInstanceCreatedNotificationInRange(
        startIndex: Int,
        endIndex: Int
    ): List<PhysicalRelationshipInstanceVariationRecord?>? {
        TODO("Not yet implemented")
    }

    override fun savePhysicalAssetRelationshipInstanceDeletedNotification(physicalRelationshipInstanceVariation: PhysicalRelationshipInstanceVariation?) {
        TODO("Not yet implemented")
    }

    override fun getPhysicalAssetRelationshipInstanceDeletedNotificationCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getPhysicalAssetRelationshipInstanceDeletedNotificationInTimeRange(
        startTimestampMs: Long,
        endTimestampMs: Long
    ): List<PhysicalRelationshipInstanceVariationRecord?>? {
        TODO("Not yet implemented")
    }

    override fun getPhysicalAssetRelationshipInstanceDeletedNotificationInRange(
        startIndex: Int,
        endIndex: Int
    ): List<PhysicalRelationshipInstanceVariationRecord?>? {
        TODO("Not yet implemented")
    }

    override fun getStorageStats(): StorageStats? {
        TODO("Not yet implemented")
    }

    override fun init() {
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }
}