package io.github.whdt.core.hdt.interfaces.physical

import io.github.whdt.core.hdt.model.property.Property
import kotlinx.serialization.Serializable

@Serializable
enum class PhysicalInterfaceType {
    MQTT,
}

@Serializable
sealed interface PhysicalInterface {
    val properties: List<Property>
    val interfaceType: PhysicalInterfaceType
}