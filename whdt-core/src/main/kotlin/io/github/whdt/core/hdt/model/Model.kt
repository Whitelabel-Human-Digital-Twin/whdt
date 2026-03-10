package io.github.whdt.core.hdt.model

import io.github.whdt.core.hdt.HdtId
import io.github.whdt.core.hdt.model.property.Property
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JvmInline @Serializable value class ModelId(val value: String) {
    override fun toString(): String {
        return value
    }
}
@JvmInline @Serializable value class ModelName(val value: String) {
    override fun toString(): String {
        return value
    }
}
@JvmInline @Serializable value class ModelDescription(val value: String) {
    override fun toString(): String {
        return value
    }
}

@Serializable
@SerialName("model")
data class Model(
    val hdtId: HdtId,
    val name: ModelName,
    val description: ModelDescription,
    val properties: List<Property>,
) {
    val id: ModelId = ModelId("$hdtId:$name")
}
