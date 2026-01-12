package io.github.whdt.distributed.message

import io.github.whdt.distributed.id.HdtIdentifier
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


@Serializable
@OptIn(ExperimentalTime::class)
data class Message(
    val namespace: String,
    val id: HdtIdentifier,
    val sentAt: Instant,
    @Transient
    val receivedAt: Instant = Clock.System.now(),
    val payload: JsonElement
) {

    /**
     * @param T the expected type for payload
     * @return the Message's deserialized payload, wrapped in a [Result]
     */
    inline fun<reified T> unwrap(): Result<T> {
        return runCatching {
            Json.decodeFromJsonElement(payload)
        }
    }
}
