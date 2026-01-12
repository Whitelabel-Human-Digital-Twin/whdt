package io.github.whdt.core.hdt.interfaces.physical

import io.github.whdt.core.hdt.model.id.HdtId
import kotlinx.serialization.Serializable

@Serializable
enum class PhysicalInterfaceType {
    MQTT,
}

@Serializable
sealed interface PhysicalInterface {
    val interfaceType: PhysicalInterfaceType
    val hdtId: HdtId
}