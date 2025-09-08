package io.github.whdt.core.hdt.model.property

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the value of a property in the system.
 */
@Serializable
sealed class PropertyValue {
    @Serializable
    @SerialName("empty-value")
    object EmptyPropertyValue: PropertyValue()
    @Serializable
    @SerialName("string-value")
    data class StringPropertyValue(val value: String) : PropertyValue()
    @Serializable
    @SerialName("int-value")
    data class IntPropertyValue(val value: Int) : PropertyValue()
    @Serializable
    @SerialName("float-value")
    data class FloatPropertyValue(val value: Float) : PropertyValue()
    @Serializable
    @SerialName("boolean-value")
    data class BooleanPropertyValue(val value: Boolean) : PropertyValue()
    @Serializable
    @SerialName("double-value")
    data class DoublePropertyValue(val value: Double) : PropertyValue()
    @Serializable
    @SerialName("long-value")
    data class LongPropertyValue(val value: Long) : PropertyValue()
}