package io.github.whdt.core.hdt.interfaces.physical

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("mqtt-physical-interface")
data class MqttPhysicalInterface(
    val broker: String = "localhost",
    val port: Int = 1883,
    val clientId: String,
) : PhysicalInterface {
    override val interfaceType: PhysicalInterfaceType = PhysicalInterfaceType.MQTT
}