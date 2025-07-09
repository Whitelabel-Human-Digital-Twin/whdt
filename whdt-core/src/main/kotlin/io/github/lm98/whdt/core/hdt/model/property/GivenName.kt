package io.github.lm98.whdt.core.hdt.model.property

import com.google.gson.Gson
import io.github.lm98.whdt.core.hdt.util.Default
import io.github.lm98.whdt.core.hdt.util.Deserialize
import io.github.lm98.whdt.core.hdt.util.Serialize

data class GivenName(
    val givenName: String,
    override val name: String = "Given Name",
    override val internalName: String = name.lowercase().replace(" ", "-"),
    override val description: String = "A name property.",
    override val id: String = "custom:given-name",
) : Property {

    override fun deserialize(value: String): Deserialize {
        return fromJson(value)
    }

    override fun serialize(s: Serialize): String {
        return toJson(s as GivenName)
    }

    companion object: Default<GivenName> {
        val gson = Gson()

        fun fromJson(json: String): GivenName {
            return gson.fromJson(json, GivenName::class.java)
        }

        fun toJson(name: GivenName): String {
            return gson.toJson(name)
        }

        override fun defaultValue(): GivenName {
            return GivenName("Default Name")
        }
    }
}