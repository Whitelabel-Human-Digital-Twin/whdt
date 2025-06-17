package hdt.interfaces.physical

import hdt.model.property.Property

enum class PhysicalInterfaceType {
    MQTT,
}

interface PhysicalInterface {
    val properties: List<Property>
    val type: PhysicalInterfaceType
}

class MqttPhysicalInterface(
    override val properties: List<Property>,
    val broker: String = "test.mosquitto.org",
    val port: Int = 1883,
    val clientId: String = "mqtt-pa-${System.currentTimeMillis()}"
) : PhysicalInterface {
    override val type: PhysicalInterfaceType = PhysicalInterfaceType.MQTT

    // Additional MQTT-specific properties or methods can be added here
}