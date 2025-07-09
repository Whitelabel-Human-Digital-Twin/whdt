package io.github.lm98.whdt.core.hdt.model.property

import com.google.gson.Gson
import io.github.lm98.whdt.core.util.Default
import io.github.lm98.whdt.core.util.Deserialize
import io.github.lm98.whdt.core.util.Serialize

data class HeartRate(
    val bpm: Int,
    override val name: String = "Heart Rate",
    override val internalName: String = "heart-rate",
    override val description: String = "The number of heartbeats per minute.",
    override val id: String = "loinc:8867-4", // LOINC code for heart rate measurement
    override val timestamp: Long = System.currentTimeMillis(),
) : InstantProperty {

    override fun deserialize(value: String): Deserialize {
        return fromJson(value)
    }

    override fun serialize(s: Serialize): String {
        return toJson(s as HeartRate)
    }

    companion object: Default<HeartRate> {
        private val gson = Gson()

        override fun defaultValue(): HeartRate {
            return HeartRate(60)
        }

        fun fromJson(json: String): HeartRate {
            return gson.fromJson(json, HeartRate::class.java)
        }

        fun toJson(heartRate: HeartRate): String {
            return gson.toJson(heartRate)
        }
    }
}