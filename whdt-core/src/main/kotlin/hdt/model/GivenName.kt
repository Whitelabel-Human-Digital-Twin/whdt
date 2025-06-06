package hdt.model

import com.google.gson.Gson
import hdt.util.Default
import hdt.util.Deserialize
import hdt.util.Serialize

data class GivenName(
    val givenName: String,
    override val name: String = "Given Name",
    override val internalName: String = name.lowercase().replace(" ", "-"),
    override val description: String = "A name property.",
    override val id: String = "custom:name",
) : Property {

    override fun defaultValue(): Default {
        return default()
    }

    override fun deserialize(value: String): Deserialize {
        return fromJson(value)
    }

    override fun serialize(s: Serialize): String {
        return toJson(s as GivenName)
    }

    companion object {
        val gson = Gson()

        fun default(): GivenName {
            return GivenName("Default Name")
        }

        fun fromJson(json: String): GivenName {
            return gson.fromJson(json, GivenName::class.java)
        }

        fun toJson(name: GivenName): String {
            return gson.toJson(name)
        }
    }
}