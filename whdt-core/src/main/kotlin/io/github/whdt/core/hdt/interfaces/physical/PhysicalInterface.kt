package io.github.whdt.core.hdt.interfaces.physical

import io.github.whdt.core.hdt.model.id.HdtId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PhysicalInterfaceType {
    MQTT,
}

@Serializable
sealed interface PhysicalInterface {
    val interfaceType: PhysicalInterfaceType
    val hdtId: HdtId
    val config: Map<String, String>
}

@Serializable
@SerialName("physical-interface-impl")
data class PhysicalInterfaceImpl(
    val id: String,
    override val interfaceType: PhysicalInterfaceType,
    override val hdtId: HdtId,
    override val config: Map<String, String> = emptyMap(),
) : PhysicalInterface