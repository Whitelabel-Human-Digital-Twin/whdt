package hdt.interfaces.physical

import hdt.model.property.Property

class MqttPhysicalInterface(
    override val properties: List<Property>,
    val broker: String = "localhost",
    val port: Int = 1883,
    val clientId: String = "mqtt-pa-${System.currentTimeMillis()}"
) : PhysicalInterface {
    override val type: PhysicalInterfaceType = PhysicalInterfaceType.MQTT

    // Additional MQTT-specific properties or methods can be added here
}