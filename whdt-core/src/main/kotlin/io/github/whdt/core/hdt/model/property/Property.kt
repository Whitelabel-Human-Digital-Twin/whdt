package io.github.whdt.core.hdt.model.property

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("property")
data class Property(
    val name: String,
    val id: String,
    val description: String,
    val valueMap: Map<String, PropertyValue> = emptyMap() // Map of value names to PropertyValue instances
)