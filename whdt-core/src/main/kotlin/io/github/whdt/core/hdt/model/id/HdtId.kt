package io.github.whdt.core.hdt.model.id

import kotlinx.serialization.Serializable

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