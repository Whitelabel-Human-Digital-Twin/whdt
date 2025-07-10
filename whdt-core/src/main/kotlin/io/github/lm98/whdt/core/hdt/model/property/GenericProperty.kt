package io.github.lm98.whdt.core.hdt.model.property

import io.github.lm98.whdt.core.util.Default
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Argument {
    @Serializable
    @SerialName("EmptyArgument")
    object EmptyArgument: Argument()
    @Serializable
    @SerialName("StringArgument")
    data class StringArgument(val value: String) : Argument()
    @Serializable
    @SerialName("IntArgument")
    data class IntArgument(val value: Int) : Argument()
    @Serializable
    @SerialName("FloatArgument")
    data class FloatArgument(val value: Float) : Argument()
    @Serializable
    @SerialName("BooleanArgument")
    data class BooleanArgument(val value: Boolean) : Argument()
}

@Serializable
data class GenericProperty(
    override val name: String,
    override val internalName: String,
    override val description: String = "A generic property that can hold any type of value.",
    override val id: String = "generic:property",
    val value: Argument = Argument.EmptyArgument // Default value as an empty argument,
): Property {

    companion object: Default<GenericProperty> {

        override fun defaultValue(): GenericProperty {
            return GenericProperty("Generic Property", "generic-property")
        }
    }
}