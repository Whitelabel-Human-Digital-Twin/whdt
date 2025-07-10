package io.github.lm98.whdt.core.hdt.model.property

import io.github.lm98.whdt.core.util.Default
import io.github.lm98.whdt.core.util.Instant
import kotlinx.serialization.Serializable

@Serializable
data class HeartRate(
    val bpm: Int,
    override val name: String = "Heart Rate",
    override val internalName: String = "heart-rate",
    override val description: String = "The number of heartbeats per minute.",
    override val id: String = "loinc:8867-4", // LOINC code for heart rate measurement
    override val timestamp: Long = System.currentTimeMillis(),
) : Property, Instant {

    companion object: Default<HeartRate> {

        override fun defaultValue(): HeartRate {
            return HeartRate(60)
        }
    }
}