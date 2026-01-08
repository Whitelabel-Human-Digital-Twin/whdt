package io.github.whdt.core.hdt.storage

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
enum class StorageType {
    IN_MEMORY,
    DB_MONGO,
    DB_POSTGRESQL,
}

@Serializable
@OptIn(ExperimentalUuidApi::class)
data class Storage(
    val id: String,
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
        fun default(): Storage {
            return Storage(
                id = Uuid.random().toString(),
                storageType = StorageType.IN_MEMORY,
            )
        }
    }
}