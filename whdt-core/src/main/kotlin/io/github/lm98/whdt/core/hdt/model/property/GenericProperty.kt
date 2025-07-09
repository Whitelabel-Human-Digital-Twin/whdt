package io.github.lm98.whdt.core.hdt.model.property

import com.google.gson.Gson
import io.github.lm98.whdt.core.util.Default
import io.github.lm98.whdt.core.util.Deserialize
import io.github.lm98.whdt.core.util.Serialize
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

    override fun deserialize(value: String): Deserialize {
        return fromJson(value)
    }

    override fun serialize(s: Serialize): String {
        return toJson(this)
    }

    companion object: Default<GenericProperty> {
        private val gson = Gson()


        fun fromJson(json: String): GenericProperty {
            return gson.fromJson(json, GenericProperty::class.java)
        }

        fun toJson(property: GenericProperty): String {
            return gson.toJson(property)
        }

        override fun defaultValue(): GenericProperty {
            return GenericProperty("Generic Property", "generic-property")
        }
    }
}