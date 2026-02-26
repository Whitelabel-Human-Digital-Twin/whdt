package io.github.whdt.dsl.builder

import io.github.whdt.core.hdt.model.id.HdtId
import io.github.whdt.core.hdt.storage.Storage
import io.github.whdt.core.hdt.storage.StorageType
import io.github.whdt.dsl.WhdtDsl

@WhdtDsl
class StorageBuilder(
    private val hdtId: HdtId,
    private val id: String,
    private val storageType: StorageType,
) {
    private val config = linkedMapOf<String, String>()

    //fun id(value: String) { id = value }

    fun config(block: ConfigBuilder.() -> Unit) {
        ConfigBuilder(config).apply(block)
    }

    fun build(): Storage =
        Storage(
            hdtId = hdtId,
            id = id, // keep your default scheme
            storageType = storageType,
            config = config.toMap(),
        )
}

@WhdtDsl
class ConfigBuilder(private val map: MutableMap<String, String>) {
    infix fun String.to(value: String) { map[this] = value }
    operator fun set(key: String, value: String) { map[key] = value }
}