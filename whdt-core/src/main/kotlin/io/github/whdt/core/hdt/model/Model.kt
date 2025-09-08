package io.github.whdt.core.hdt.model

import io.github.whdt.core.hdt.model.property.Property
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("model")
data class Model(
    val properties: List<Property>,
)
