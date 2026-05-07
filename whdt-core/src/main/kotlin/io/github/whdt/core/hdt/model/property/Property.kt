package io.github.whdt.core.hdt.model.property

import io.github.whdt.core.hdt.HdtIdFactory
import io.github.whdt.core.hdt.model.ModelId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@JvmInline @Serializable value class PropertyId(val value: String) {
    override fun toString(): String = value
}

@JvmInline @Serializable value class PropertyName(val value: String) {
    init {
        require(value.isNotBlank()) { "PropertyName must not be blank" }
        require(':' !in value) { "PropertyName must not contain ':'" }
    }

    override fun toString(): String = value
}

@JvmInline @Serializable value class PropertyDescription(val value: String) {
    override fun toString(): String = value
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
    val id: PropertyId = HdtIdFactory.propertyId(modelId, name)
}