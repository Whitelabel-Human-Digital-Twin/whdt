package io.github.lm98.whdt.core.hdt.model.property

import com.google.gson.Gson
import io.github.lm98.whdt.core.util.Default
import io.github.lm98.whdt.core.util.Deserialize
import io.github.lm98.whdt.core.util.Serialize

data class BloodPressure(
    val systolic: Int,
    val diastolic: Int,
    override val name: String = "Blood Pressure",
    override val internalName: String = "blood-pressure",
    override val description: String = "The pressure of blood in the circulatory system.",
    override val id: String = "loinc:8480-6", // LOINC code for blood pressure measurement,
    override val timestamp: Long = System.currentTimeMillis(),

) : InstantProperty {

    override fun deserialize(value: String): Deserialize {
        return fromJson(value)
    }

    override fun serialize(s: Serialize): String {
        return toJson(s as BloodPressure)
    }

    companion object: Default<BloodPressure> {
        private val gson = Gson()

        fun fromJson(json: String): BloodPressure {
            return gson.fromJson(json, BloodPressure::class.java)
        }

        fun toJson(bloodPressure: BloodPressure): String {
            return gson.toJson(bloodPressure)
        }

        override fun defaultValue(): BloodPressure {
            return BloodPressure(120, 80)
        }
    }
}