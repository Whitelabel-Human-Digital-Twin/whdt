package io.github.whdt.core.hdt.interfaces.physical

import io.github.whdt.core.hdt.HdtId
import io.github.whdt.core.hdt.HdtIdFactory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PhysicalInterfaceType {
    MQTT,
}

@JvmInline @Serializable value class PhysicalInterfaceId(val value: String) {
    override fun toString(): String = value
}

@JvmInline @Serializable value class PhysicalInterfaceName(val value: String) {
    init {
        require(value.isNotBlank()) { "PhysicalInterfaceName must not be blank" }
        require(':' !in value) { "PhysicalInterfaceName must not contain ':'" }
    }

    override fun toString(): String = value
}

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
    override val id = HdtIdFactory.physicalInterfaceId(hdtId, name)
}