package io.github.lm98.whdt.core.hdt.interfaces.physical

import io.github.lm98.whdt.core.hdt.model.property.Property

enum class PhysicalInterfaceType {
    MQTT,
}

interface PhysicalInterface {
    val properties: List<Property>
    val type: PhysicalInterfaceType
}