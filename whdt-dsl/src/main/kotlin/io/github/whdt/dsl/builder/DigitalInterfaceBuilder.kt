package io.github.whdt.dsl.builder

import io.github.whdt.core.hdt.interfaces.digital.DigitalInterface
import io.github.whdt.core.hdt.interfaces.digital.DigitalInterfaceImpl
import io.github.whdt.core.hdt.interfaces.digital.DigitalInterfaceType
import io.github.whdt.core.hdt.model.id.HdtId
import io.github.whdt.dsl.WhdtDsl

@WhdtDsl
class DigitalInterfaceBuilder(
    private val hdtId: HdtId,
    private val id: String
) {
    private var type: DigitalInterfaceType = DigitalInterfaceType.MQTT
    private val config = linkedMapOf<String, String>()

    fun type(value: DigitalInterfaceType) { type = value }

    fun config(block: ConfigBuilder.() -> Unit) {
        ConfigBuilder(config).apply(block)
    }

    fun build(): DigitalInterface =
        DigitalInterfaceImpl(
            id = id,
            interfaceType = type,
            hdtId = hdtId,
            config = config.toMap()
        )
}