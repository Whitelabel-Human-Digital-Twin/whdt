package io.github.lm98.whdt.core.serde

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

interface SerDe<T> {
    val serializer: KSerializer<T>
    fun serialize(value: T): String
    fun deserialize(data: String): T
}

inline fun <reified T> jsonSerDe(json: Json = Json): SerDe<T> {
    val ser = json.serializersModule.serializer<T>()
    return object : SerDe<T> {
        override val serializer = ser
        override fun serialize(value: T): String = json.encodeToString(serializer, value)
        override fun deserialize(data: String): T = json.decodeFromString(serializer, data)
    }
}
