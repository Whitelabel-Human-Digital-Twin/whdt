package io.github.lm98.whdt.core.hdt.model.property

import com.google.gson.Gson
import io.github.lm98.whdt.core.util.Default
import io.github.lm98.whdt.core.util.Deserialize
import io.github.lm98.whdt.core.util.Serialize
import kotlinx.serialization.Serializable

@Serializable
data class Surname(
    val surname: String,
    override val name: String = "Surname",
    override val internalName: String = name.lowercase().replace(" ", "-"),
    override val description: String = "A name property.",
    override val id: String = "custom:surname",
) : Property {

    override fun deserialize(value: String): Deserialize {
        return fromJson(value)
    }

    override fun serialize(s: Serialize): String {
        return toJson(s as Surname)
    }

    companion object: Default<Surname> {
        val gson = Gson()

        override fun defaultValue(): Surname {
            return Surname("Default Name")
        }

        fun fromJson(json: String): Surname {
            return gson.fromJson(json, Surname::class.java)
        }

        fun toJson(name: Surname): String {
            return gson.toJson(name)
        }
    }
}