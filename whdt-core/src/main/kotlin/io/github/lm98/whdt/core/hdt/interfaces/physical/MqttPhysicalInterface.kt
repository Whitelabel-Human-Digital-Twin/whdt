package io.github.lm98.whdt.core.hdt.interfaces.physical

import io.github.lm98.whdt.core.hdt.model.property.Property
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("mqtt-physical-interface")
data class MqttPhysicalInterface(
    override val properties: List<Property>,
    val broker: String = "localhost",
    val port: Int = 1883,
    val clientId: String = "mqtt-pa-${System.currentTimeMillis()}"
) : PhysicalInterface {
    override val interfaceType: PhysicalInterfaceType = PhysicalInterfaceType.MQTT

    // Additional MQTT-specific properties or methods can be added here
}