package io.github.whdt.dsl.builder

import io.github.whdt.core.hdt.HumanDigitalTwin
import io.github.whdt.core.hdt.interfaces.digital.DigitalInterface
import io.github.whdt.core.hdt.interfaces.physical.PhysicalInterface
import io.github.whdt.core.hdt.model.Model
import io.github.whdt.core.hdt.model.id.HdtId
import io.github.whdt.core.hdt.storage.Storage
import io.github.whdt.core.hdt.storage.StorageType
import io.github.whdt.dsl.WhdtDsl

@WhdtDsl
class HumanDigitalTwinBuilder(private val hdtId: HdtId) {
    private val models = mutableListOf<Model>()
    private val digitalInterfaces = mutableListOf<DigitalInterface>()
    private val physicalInterfaces = mutableListOf<PhysicalInterface>()
    private val storages = mutableListOf<Storage>()

    fun model(modelId: String, block: ModelBuilder.() -> Unit) {
        models += ModelBuilder(modelId).apply(block).build()
    }

    fun digitalInterface(
        id: String,
        block: DigitalInterfaceBuilder.() -> Unit
    ) {
        digitalInterfaces += DigitalInterfaceBuilder(hdtId, id).apply(block).build()
    }

    fun physicalInterface(
        id: String,
        block: PhysicalInterfaceBuilder.() -> Unit
    ) {
        physicalInterfaces += PhysicalInterfaceBuilder(hdtId, id).apply(block).build()
    }

    fun storage(
        id: String,
        type: StorageType,
        block: StorageBuilder.() -> Unit = {}
    ) {
        storages += StorageBuilder(hdtId, type).apply(block).build()
    }

    fun build(): HumanDigitalTwin =
        HumanDigitalTwin(
            hdtId = hdtId,
            models = models.toList(),
            digitalInterfaces = digitalInterfaces.toList(),
            physicalInterfaces = physicalInterfaces.toList(),
            storages = if (storages.isEmpty()) listOf(Storage.default(hdtId)) else storages.toList()
        )
}