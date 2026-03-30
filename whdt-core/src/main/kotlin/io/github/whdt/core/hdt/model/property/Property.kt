package io.github.whdt.core.hdt.model.property

import io.github.whdt.core.hdt.model.ModelId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@JvmInline @Serializable value class PropertyId(val value: String) {
    override fun toString(): String {
        return value
    }
}
@JvmInline @Serializable value class PropertyName(val value: String) {
    override fun toString(): String {
        return value
    }
}
@JvmInline @Serializable value class PropertyDescription(val value: String) {
    override fun toString(): String {
        return value
    }
}

@Serializable
@SerialName("property")
data class Property(
    val modelId: ModelId,
    val name: PropertyName,
    val description: PropertyDescription,
    val timestamp: Instant,
    val value: PropertyValue,
    val metadata: Map<String, String> = emptyMap(),
) {
    val id: PropertyId = PropertyId("$modelId:$name")
}