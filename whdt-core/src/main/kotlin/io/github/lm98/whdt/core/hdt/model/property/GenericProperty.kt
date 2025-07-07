package io.github.lm98.whdt.core.hdt.model.property

import com.google.gson.Gson
import io.github.lm98.whdt.core.hdt.util.Default
import io.github.lm98.whdt.core.hdt.util.Deserialize
import io.github.lm98.whdt.core.hdt.util.Serialize

sealed interface Argument

data class StringArgument(val value: String) : Argument

data class IntArgument(val value: Int) : Argument

data class FloatArgument(val value: Float) : Argument

data class BooleanArgument(val value: Boolean) : Argument

data class EmptyArgument(val value: Unit) : Argument

data class GenericProperty(
    override val name: String,
    override val internalName: String,
    override val description: String = "A generic property that can hold any type of value.",
    override val id: String = "generic:property",
    val value: Argument = EmptyArgument(Unit) // Default value as an empty argument,
): Property {
    override fun defaultValue(): Default {
        return default()
    }

    override fun deserialize(value: String): Deserialize {
        return fromJson(value)
    }

    override fun serialize(s: Serialize): String {
        return toJson(this)
    }

    companion object {
        private val gson = Gson()

        fun default(): GenericProperty {
            return GenericProperty("Generic Property", "generic-property")
        }

        fun fromJson(json: String): GenericProperty {
            return gson.fromJson(json, GenericProperty::class.java)
        }

        fun toJson(property: GenericProperty): String {
            return gson.toJson(property)
        }
    }
}