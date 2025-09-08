package io.github.whdt.core.hdt.interfaces.digital

import io.github.whdt.core.hdt.model.property.Property
import kotlinx.serialization.Serializable

@Serializable
enum class DigitalInterfaceType {
    MQTT,
    HTTP,
}

@Serializable
sealed interface DigitalInterface {
    val interfaceType: DigitalInterfaceType
    val properties: List<Property>
}