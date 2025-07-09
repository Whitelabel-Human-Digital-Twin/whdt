package io.github.lm98.whdt.core.hdt.model.property

import com.google.gson.Gson
import io.github.lm98.whdt.core.hdt.util.Default
import io.github.lm98.whdt.core.hdt.util.Deserialize
import io.github.lm98.whdt.core.hdt.util.Serialize

data class BloodOxygen(
    val percentage: Int,
    override val name: String = "Blood Oxygen",
    override val internalName: String = "blood-oxygen",
    override val description: String = "The level of oxygen in the blood.",
    override val id: String = "loinc:2708-6", // LOINC code for oxygen saturation measurement
    override val timestamp: Long = System.currentTimeMillis(),
    val timezone: Int = 0,
) : InstantProperty {

    override fun deserialize(value: String): Deserialize {
        return fromJson(value)
    }

    override fun serialize(s: Serialize): String {
        return toJson(s as BloodOxygen)
    }

    companion object: Default<BloodOxygen> {
        private val gson = Gson()

        fun fromJson(json: String): BloodOxygen {
            return gson.fromJson(json, BloodOxygen::class.java)
        }

        fun toJson(bloodOxygen: BloodOxygen): String {
            return gson.toJson(bloodOxygen)
        }

        override fun defaultValue(): BloodOxygen {
            return BloodOxygen(95)
        }
    }
}