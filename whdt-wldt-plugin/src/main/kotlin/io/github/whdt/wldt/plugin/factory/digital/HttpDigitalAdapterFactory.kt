package io.github.whdt.wldt.plugin.factory.digital

import io.github.whdt.core.hdt.interfaces.digital.DigitalInterface
import io.github.whdt.core.hdt.interfaces.digital.DigitalInterfaceType
import io.github.whdt.core.hdt.model.property.Property
import it.wldt.adapter.http.digital.adapter.HttpDigitalAdapter
import it.wldt.adapter.http.digital.adapter.HttpDigitalAdapterConfiguration
import it.wldt.core.engine.DigitalTwin

class HttpDigitalAdapterFactory : DigitalAdapterFactory {
    override val interfaceType = DigitalInterfaceType.HTTP

    override fun validate(dI: DigitalInterface): Result<Unit> = runCatching {
        dI.optionalString("host", DEFAULT_HOST)
        dI.optionalInt("port", DEFAULT_PORT)
    }

    override fun create(dI: DigitalInterface, dt: DigitalTwin, properties: List<Property>): HttpDigitalAdapter {
        val host = dI.optionalString("host", DEFAULT_HOST)
        val port = dI.optionalInt("port", DEFAULT_PORT)
        val httpConfig = HttpDigitalAdapterConfiguration(dI.id.toString(), host, port)
        httpConfig.addPropertiesFilter(properties.map { it.id.toString() })
        return HttpDigitalAdapter(httpConfig, dt)
    }

    private companion object {
        const val DEFAULT_HOST = "localhost"
        const val DEFAULT_PORT = 8080
    }
}
