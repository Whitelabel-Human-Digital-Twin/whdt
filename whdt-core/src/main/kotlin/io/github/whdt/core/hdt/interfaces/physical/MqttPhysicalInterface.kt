package io.github.whdt.core.hdt.interfaces.physical

import io.github.whdt.core.hdt.model.id.HdtId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
@SerialName("mqtt-physical-interface")
@OptIn(ExperimentalUuidApi::class)
data class MqttPhysicalInterface(
    override val hdtId: HdtId,
    val broker: String = "localhost",
    val port: Int = 1883,
    val id: String = "$hdtId-mqtt-physical-interface-${Uuid.random()}",
) : PhysicalInterface {
    override val interfaceType: PhysicalInterfaceType = PhysicalInterfaceType.MQTT
}