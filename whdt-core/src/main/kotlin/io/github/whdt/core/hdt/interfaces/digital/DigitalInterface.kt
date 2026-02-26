package io.github.whdt.core.hdt.interfaces.digital

import io.github.whdt.core.hdt.model.id.HdtId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DigitalInterfaceType {
    MQTT,
    HTTP,
}

@Serializable
sealed interface DigitalInterface {
    val interfaceType: DigitalInterfaceType
    val hdtId: HdtId
    val config: Map<String, String>
}

@Serializable
@SerialName("digital-interface-impl")
data class DigitalInterfaceImpl(
    val id: String,
    override val interfaceType: DigitalInterfaceType,
    override val hdtId: HdtId,
    override val config: Map<String, String> = emptyMap(),
) : DigitalInterface