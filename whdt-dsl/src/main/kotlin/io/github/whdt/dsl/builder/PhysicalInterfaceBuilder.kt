package io.github.whdt.dsl.builder

import io.github.whdt.core.hdt.interfaces.physical.PhysicalInterface
import io.github.whdt.core.hdt.interfaces.physical.PhysicalInterfaceImpl
import io.github.whdt.core.hdt.interfaces.physical.PhysicalInterfaceType
import io.github.whdt.core.hdt.model.id.HdtId
import io.github.whdt.dsl.WhdtDsl

@WhdtDsl
class PhysicalInterfaceBuilder(
    private val hdtId: HdtId,
    private val id: String
) {
    private var type: PhysicalInterfaceType = PhysicalInterfaceType.MQTT
    private val config = linkedMapOf<String, String>()

    fun type(value: PhysicalInterfaceType) { type = value }

    fun config(block: ConfigBuilder.() -> Unit) {
        ConfigBuilder(config).apply(block)
    }

    fun build(): PhysicalInterface =
        PhysicalInterfaceImpl(
            id = id,
            interfaceType = type,
            hdtId = hdtId,
            config = config.toMap()
        )
}