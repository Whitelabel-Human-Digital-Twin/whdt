package io.github.whdt.core.hdt.interfaces.digital

import io.github.whdt.core.hdt.model.id.HdtId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
@SerialName("http-digital-interface")
@OptIn(ExperimentalUuidApi::class)
data class HttpDigitalInterface(
    override val hdtId: HdtId,
    val host: String = "localhost",
    val port: Int = 8080,
    val id: String = "$hdtId-http-digital-interface-${Uuid.random()}",
) : DigitalInterface {
    override val interfaceType: DigitalInterfaceType = DigitalInterfaceType.HTTP
}