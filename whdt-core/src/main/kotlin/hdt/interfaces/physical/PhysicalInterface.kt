package hdt.interfaces.physical

import hdt.model.property.Property

enum class PhysicalInterfaceType {
    MQTT,
}

interface PhysicalInterface {
    val properties: List<Property>
    val type: PhysicalInterfaceType
}