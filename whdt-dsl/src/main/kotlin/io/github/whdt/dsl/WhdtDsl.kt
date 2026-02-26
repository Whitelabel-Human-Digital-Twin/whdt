package io.github.whdt.dsl

import io.github.whdt.core.hdt.HumanDigitalTwin
import io.github.whdt.core.hdt.model.id.HdtId
import io.github.whdt.dsl.builder.HumanDigitalTwinBuilder

@DslMarker
annotation class WhdtDsl

fun hdt(hdtIdRaw: String, block: HumanDigitalTwinBuilder.() -> Unit): HumanDigitalTwin {
    val hdtId = HdtId(hdtIdRaw) // adapt to your actual type
    return HumanDigitalTwinBuilder(hdtId).apply(block).build()
}