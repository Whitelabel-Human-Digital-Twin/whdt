package hdt.model

import com.google.gson.Gson
import hdt.util.Default
import hdt.util.Deserialize
import hdt.util.Serialize

data class HeartRate(
    val bpm: Int,
    override val name: String = "Heart Rate",
    override val internalName: String = "heart-rate",
    override val description: String = "The number of heartbeats per minute.",
    override val id: String = "loinc:8867-4", // LOINC code for heart rate measurement
    override val timestamp: Long = System.currentTimeMillis(),
) : Property {

    override fun defaultValue(): Default {
        return default()
    }

    override fun deserialize(value: String): Deserialize {
        return fromJson(value)
    }

    override fun serialize(s: Serialize): String {
        return toJson(s as HeartRate)
    }

    companion object {
        val gson = Gson()

        fun default(): HeartRate {
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