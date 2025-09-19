package io.github.whdt.core.hdt.interfaces.digital

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("http-digital-interface")
data class HttpDigitalInterface(
    val host: String = "localhost",
    val port: Int = 8080,
    val id: String,
) : DigitalInterface {
    override val interfaceType: DigitalInterfaceType = DigitalInterfaceType.HTTP
}