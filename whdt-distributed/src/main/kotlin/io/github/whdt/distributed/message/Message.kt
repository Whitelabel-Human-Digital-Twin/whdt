package io.github.whdt.distributed.message

import io.github.whdt.distributed.id.HdtIdentifier
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement

@Serializable
data class Message(val namespace: String, val id: HdtIdentifier, val payload: JsonElement) {

    inline fun<reified T> unwrap(): Result<T> {
        return runCatching {
            Json.decodeFromJsonElement(payload)
        }
    }
}
