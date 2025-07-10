package io.github.lm98.whdt.core.hdt.interfaces.digital

import io.github.lm98.whdt.core.hdt.model.property.Property
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("http-digital-interface")
data class HttpDigitalInterface(
    override val properties: List<Property>,
    val host: String = "localhost",
    val port: Int = 8080,
    val id: String = "http-di-${System.currentTimeMillis()}",
) : DigitalInterface  {
    override val interfaceType: DigitalInterfaceType = DigitalInterfaceType.HTTP
}