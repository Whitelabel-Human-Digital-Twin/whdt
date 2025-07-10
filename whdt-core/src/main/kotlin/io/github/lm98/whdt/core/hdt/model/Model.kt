package io.github.lm98.whdt.core.hdt.model

import io.github.lm98.whdt.core.hdt.model.property.Property
import kotlinx.serialization.Serializable

@Serializable
data class Model(
    val properties: List<Property>,
)
