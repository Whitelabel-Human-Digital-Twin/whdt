package hdt.interfaces.digital

import hdt.model.property.Property

enum class DigitalInterfaceType {
    MQTT,
    HTTP,
    WEBSOCKET,
    // Add other digital interface types as needed
}

interface DigitalInterface {
    val type: DigitalInterfaceType
    val properties: List<Property>
}

class MqttDigitalInterface(
    override val properties: List<Property>,
    val broker: String = "localhost",
    val port: Int = 1883,
    val clientId: String = "mqtt-di-${System.currentTimeMillis()}"
) : DigitalInterface {
    override val type: DigitalInterfaceType = DigitalInterfaceType.MQTT

    // Additional MQTT-specific properties or methods can be added here
}