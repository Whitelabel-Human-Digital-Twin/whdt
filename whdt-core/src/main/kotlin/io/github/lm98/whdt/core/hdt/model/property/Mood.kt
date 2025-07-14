package io.github.lm98.whdt.core.hdt.model.property

import io.github.lm98.whdt.core.util.Default
import io.github.lm98.whdt.core.util.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("mood")
data class Mood(
    val mood: String,
    val energyLevel: Int = 50, // Scale from 1 to 100
    override val name: String = "Mood",
    override val internalName: String = "mood",
    override val description: String = "The emotional state of the individual.",
    override val id: String = "hdt:00001-1", // custom code for mood assessment
    override val timestamp: Long = System.currentTimeMillis(),
): Property, Instant {

    companion object: Default<Mood> {

        override fun defaultValue(): Mood {
            return Mood("Neutral")
        }
    }
}