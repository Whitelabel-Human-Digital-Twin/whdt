package io.github.whdt.core.hdt.interfaces.digital

import kotlinx.serialization.Serializable

@Serializable
enum class DigitalInterfaceType {
    MQTT,
    HTTP,
}

@Serializable
sealed interface DigitalInterface {
    val interfaceType: DigitalInterfaceType
}