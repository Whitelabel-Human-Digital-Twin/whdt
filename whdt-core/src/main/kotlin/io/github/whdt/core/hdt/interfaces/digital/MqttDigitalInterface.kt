package io.github.whdt.core.hdt.interfaces.digital

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("mqtt-digital-interface")
data class MqttDigitalInterface(
    val broker: String = "localhost",
    val port: Int = 1883,
    val clientId: String,
) : DigitalInterface {
    override val interfaceType: DigitalInterfaceType = DigitalInterfaceType.MQTT

    // Additional MQTT-specific properties or methods can be added here
}