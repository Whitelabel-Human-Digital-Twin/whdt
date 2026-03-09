package io.github.whdt.core.hdt.interfaces.digital

import io.github.whdt.core.hdt.HdtId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("http-digital-interface")
data class HttpDigitalInterface(
    override val hdtId: HdtId,
    override val name: DigitalInterfaceName,
    val host: String = "localhost",
    val port: Int = 8080,
    override val config: Map<String, String> = emptyMap(),
) : DigitalInterface {
    override val interfaceType: DigitalInterfaceType = DigitalInterfaceType.HTTP
    override val id = DigitalInterfaceId("$hdtId:$name")
}