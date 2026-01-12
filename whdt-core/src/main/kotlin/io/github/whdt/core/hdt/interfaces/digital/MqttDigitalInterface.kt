package io.github.whdt.core.hdt.interfaces.digital

import io.github.whdt.core.hdt.model.id.HdtId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
@SerialName("mqtt-digital-interface")
@OptIn(ExperimentalUuidApi::class)
data class MqttDigitalInterface(
    override val hdtId: HdtId,
    val broker: String = "localhost",
    val port: Int = 1883,
    val id: String = "$hdtId-mqtt-digital-interface-${Uuid.random()}",
) : DigitalInterface {
    override val interfaceType: DigitalInterfaceType = DigitalInterfaceType.MQTT

    // Additional MQTT-specific properties or methods can be added here
}