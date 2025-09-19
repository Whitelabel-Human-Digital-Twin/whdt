package io.github.whdt.core.hdt.interfaces.physical

import kotlinx.serialization.Serializable

@Serializable
enum class PhysicalInterfaceType {
    MQTT,
}

@Serializable
sealed interface PhysicalInterface {
    val interfaceType: PhysicalInterfaceType
}