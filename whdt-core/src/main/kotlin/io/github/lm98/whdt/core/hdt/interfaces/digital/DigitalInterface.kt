package io.github.lm98.whdt.core.hdt.interfaces.digital

import io.github.lm98.whdt.core.hdt.model.property.Property

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