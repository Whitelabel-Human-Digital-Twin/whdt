package io.github.whdt.distributed.id

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@JvmInline
@Serializable
value class HdtId(val id: String) {
    companion object {
        fun of(raw: String): HdtId {
            require(raw.isNotEmpty())
            require(raw.isNotBlank())
            require(raw.length <= 64)
            return HdtId(raw)
        }
    }
}
