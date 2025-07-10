package io.github.lm98.whdt.core.hdt.model.property

import io.github.lm98.whdt.core.util.Default
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class PropertyValue {
    @Serializable
    @SerialName("EmptyArgument")
    object EmptyPropertyValue: PropertyValue()
    @Serializable
    @SerialName("StringArgument")
    data class StringPropertyValue(val value: String) : PropertyValue()
    @Serializable
    @SerialName("IntArgument")
    data class IntPropertyValue(val value: Int) : PropertyValue()
    @Serializable
    @SerialName("FloatArgument")
    data class FloatPropertyValue(val value: Float) : PropertyValue()
    @Serializable
    @SerialName("BooleanArgument")
    data class BooleanPropertyValue(val value: Boolean) : PropertyValue()
}

@Serializable
data class GenericProperty(
    override val name: String,
    override val internalName: String,
    override val description: String = "A generic property that can hold any type of value.",
    override val id: String = "generic:property",
    val value: PropertyValue = PropertyValue.EmptyPropertyValue // Default value as an empty argument,
): Property {

    companion object: Default<GenericProperty> {

        override fun defaultValue(): GenericProperty {
            return GenericProperty("Generic Property", "generic-property")
        }
    }
}