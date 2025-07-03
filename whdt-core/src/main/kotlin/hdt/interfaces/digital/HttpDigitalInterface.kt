package hdt.interfaces.digital

import hdt.model.property.Property

class HttpDigitalInterface(
    override val properties: List<Property>,
    val host: String = "localhost",
    val port: Int = 8080,
) : DigitalInterface  {
    override val type: DigitalInterfaceType = DigitalInterfaceType.HTTP
}