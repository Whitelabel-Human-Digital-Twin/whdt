package io.github.lm98.whdt.core.hdt.interfaces.digital

import io.github.lm98.whdt.core.hdt.model.property.Property

class HttpDigitalInterface(
    override val properties: List<Property>,
    val host: String = "localhost",
    val port: Int = 8080,
    val id: String = "http-di-${System.currentTimeMillis()}",
) : DigitalInterface  {
    override val type: DigitalInterfaceType = DigitalInterfaceType.HTTP
}