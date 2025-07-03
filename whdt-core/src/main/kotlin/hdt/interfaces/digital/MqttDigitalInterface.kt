package hdt.interfaces.digital

import hdt.model.property.Property

class MqttDigitalInterface(
    override val properties: List<Property>,
    val broker: String = "localhost",
    val port: Int = 1883,
    val clientId: String = "mqtt-di-${System.currentTimeMillis()}"
) : DigitalInterface {
    override val type: DigitalInterfaceType = DigitalInterfaceType.MQTT

    // Additional MQTT-specific properties or methods can be added here
}