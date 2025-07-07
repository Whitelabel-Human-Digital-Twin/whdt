package io.github.lm98.whdt.core.hdt.model.property

import com.google.gson.Gson
import io.github.lm98.whdt.core.hdt.util.Default
import io.github.lm98.whdt.core.hdt.util.Deserialize
import io.github.lm98.whdt.core.hdt.util.Serialize

data class Mood(
    val mood: String,
    val energyLevel: Int = 50, // Scale from 1 to 100
    override val name: String = "Mood",
    override val internalName: String = "mood",
    override val description: String = "The emotional state of the individual.",
    override val id: String = "hdt:00001-1", // custom code for mood assessment
    override val timestamp: Long = System.currentTimeMillis(),
): InstantProperty {

    override fun defaultValue(): Default {
        return default()
    }

    override fun deserialize(value: String): Deserialize {
        return fromJson(value)
    }

    override fun serialize(s: Serialize): String {
        return toJson(s as Mood)
    }

    companion object {
        private val gson = Gson()

        fun default(): Mood {
            return Mood("Neutral")
        }

        fun fromJson(json: String): Mood {
            return gson.fromJson(json, Mood::class.java)
        }

        fun toJson(mood: Mood): String {
            return gson.toJson(mood)
        }
    }
}