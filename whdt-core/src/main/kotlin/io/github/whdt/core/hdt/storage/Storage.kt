package io.github.whdt.core.hdt.storage

import io.github.whdt.core.hdt.HdtId
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
enum class StorageType {
    IN_MEMORY,
    DB_MONGO,
    DB_POSTGRESQL,
}

@JvmInline @Serializable value class StorageName(val value: String) {
    override fun toString(): String {
        return value
    }
}

@JvmInline @Serializable value class StorageId(val value: String) {
    override fun toString(): String {
        return value
    }
}

@Serializable
data class Storage(
    val hdtId: HdtId,
    val name: StorageName,
    val storageType: StorageType,
    @Transient
    val config: Map<String, String> = emptyMap(),
) {
    val id = StorageId("$hdtId:$name")

    fun addConfig(c: Map<String, String>): Storage {
        return copy(config = config + c)
    }

    fun addConfigProperty(p: Pair<String, String>): Storage {
        return copy(config = config.plus(p))
    }

    fun removeConfigProperty(p: String): Storage {
        return copy(config = config.minus(p))
    }

    companion object {
        fun default(hdtId: HdtId): Storage {
            return Storage(
                hdtId = hdtId,
                name = StorageName("memory-storage"),
                storageType = StorageType.IN_MEMORY,
            )
        }
    }
}