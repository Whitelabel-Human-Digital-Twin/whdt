package io.github.lm98.whdt.core.hdt.model.property

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("property")
data class Property(
    val name: String,
    val id: String,
    val description: String,
    val valueMap: Map<String, PropertyValue> = emptyMap() // Map of value names to PropertyValue instances
) {
    companion object {
        fun defaultValue(): Property {
            return Property(
                name = "Default Property",
                id = "default-property",
                description = "A default property with no specific values.",
                valueMap = mapOf("default" to PropertyValue.EmptyPropertyValue)
            )
        }
    }
}