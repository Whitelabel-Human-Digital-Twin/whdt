package io.github.whdt.core.hdt.interfaces.physical

import io.github.whdt.core.hdt.HdtId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PhysicalInterfaceType {
    MQTT,
}

@JvmInline @Serializable value class PhysicalInterfaceId(val value: String)
@JvmInline @Serializable value class PhysicalInterfaceName(val value: String)

@Serializable
sealed interface PhysicalInterface {
    val interfaceType: PhysicalInterfaceType
    val hdtId: HdtId
    val name: PhysicalInterfaceName
    val id: PhysicalInterfaceId
    val config: Map<String, String>
}

@Serializable
@SerialName("physical-interface-impl")
data class PhysicalInterfaceImpl(
    override val interfaceType: PhysicalInterfaceType,
    override val hdtId: HdtId,
    override val name: PhysicalInterfaceName,
    override val config: Map<String, String> = emptyMap(),
) : PhysicalInterface {
    override val id = PhysicalInterfaceId("$hdtId:$name")
}