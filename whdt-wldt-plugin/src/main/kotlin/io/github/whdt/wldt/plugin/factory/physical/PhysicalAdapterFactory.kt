package io.github.whdt.wldt.plugin.factory.physical

import io.github.whdt.core.hdt.interfaces.physical.PhysicalInterface
import io.github.whdt.core.hdt.interfaces.physical.PhysicalInterfaceType
import io.github.whdt.core.hdt.model.property.Property
import it.wldt.adapter.physical.PhysicalAdapter

interface PhysicalAdapterFactory {
    val interfaceType: PhysicalInterfaceType

    /**
     * Returns Result.success(Unit) if the config is well-formed for this factory.
     * Returns Result.failure(ConfigException) if any required key is missing or any present key is malformed.
     * Validation must not have side effects.
     */
    fun validate(pI: PhysicalInterface): Result<Unit>

    /**
     * Constructs the adapter. Caller must validate first; create() may throw on bad config.
     */
    fun create(pI: PhysicalInterface, properties: List<Property>): PhysicalAdapter
}
