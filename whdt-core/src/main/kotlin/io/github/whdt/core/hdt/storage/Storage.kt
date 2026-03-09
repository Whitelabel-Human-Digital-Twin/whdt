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

@JvmInline @Serializable value class StorageId(val value: String)

@Serializable
data class Storage(
    val hdtId: HdtId,
    val id: StorageId = StorageId("Generic Storage for $hdtId"),
    val storageType: StorageType,
    @Transient
    val config: Map<String, String> = emptyMap(),
) {
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
                storageType = StorageType.IN_MEMORY,
            )
        }
    }
}