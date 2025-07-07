package io.github.lm98.whdt.core.hdt.model.property

import com.google.gson.Gson
import io.github.lm98.whdt.core.hdt.util.Default
import io.github.lm98.whdt.core.hdt.util.Deserialize
import io.github.lm98.whdt.core.hdt.util.Serialize

data class Surname(
    val surname: String,
    override val name: String = "Surname",
    override val internalName: String = name.lowercase().replace(" ", "-"),
    override val description: String = "A name property.",
    override val id: String = "custom:surname",
) : Property {

    override fun defaultValue(): Default {
        return default()
    }

    override fun deserialize(value: String): Deserialize {
        return fromJson(value)
    }

    override fun serialize(s: Serialize): String {
        return toJson(s as Surname)
    }

    companion object {
        val gson = Gson()

        fun default(): Surname {
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