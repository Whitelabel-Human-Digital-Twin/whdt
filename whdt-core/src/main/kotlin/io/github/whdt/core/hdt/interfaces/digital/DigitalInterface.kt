package io.github.whdt.core.hdt.interfaces.digital

import io.github.whdt.core.hdt.HdtId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DigitalInterfaceType {
    MQTT,
    HTTP,
}

@JvmInline @Serializable value class DigitalInterfaceId(val value: String) {
    override fun toString(): String {
        return value
    }
}
@JvmInline @Serializable value class DigitalInterfaceName(val value: String) {
    override fun toString(): String {
        return value
    }
}

@Serializable
sealed interface DigitalInterface {
    val interfaceType: DigitalInterfaceType
    val hdtId: HdtId
    val name: DigitalInterfaceName
    val id: DigitalInterfaceId
    val config: Map<String, String>
}

@Serializable
@SerialName("digital-interface-impl")
data class DigitalInterfaceImpl(
    override val interfaceType: DigitalInterfaceType,
    override val hdtId: HdtId,
    override val name: DigitalInterfaceName,
    override val config: Map<String, String> = emptyMap(),
) : DigitalInterface {
    override val id = DigitalInterfaceId("$hdtId:$name")
}