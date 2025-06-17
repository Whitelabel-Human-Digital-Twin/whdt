package hdt.model.property

import com.google.gson.Gson
import hdt.util.Default
import hdt.util.Deserialize
import hdt.util.Serialize

data class BloodOxygen(
    val percentage: Int,
    override val name: String = "Blood Oxygen",
    override val internalName: String = "blood-oxygen",
    override val description: String = "The level of oxygen in the blood.",
    override val id: String = "loinc:2708-6", // LOINC code for oxygen saturation measurement
    override val timestamp: Long = System.currentTimeMillis(),
    val timezone: Int = 0,
) : InstantProperty {

    override fun defaultValue(): Default {
        return default()
    }

    override fun deserialize(value: String): Deserialize {
        return fromJson(value)
    }

    override fun serialize(s: Serialize): String {
        return toJson(s as BloodOxygen)
    }

    companion object {
        private val gson = Gson()

        fun default(): BloodOxygen {
            return BloodOxygen(95)
        }

        fun fromJson(json: String): BloodOxygen {
            return gson.fromJson(json, BloodOxygen::class.java)
        }

        fun toJson(bloodOxygen: BloodOxygen): String {
            return gson.toJson(bloodOxygen)
        }
    }
}