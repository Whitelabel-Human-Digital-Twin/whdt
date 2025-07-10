package io.github.lm98.whdt.core.hdt.model.property

import io.github.lm98.whdt.core.util.Default
import io.github.lm98.whdt.core.util.Instant
import kotlinx.serialization.Serializable

@Serializable
data class BloodPressure(
    val systolic: Int,
    val diastolic: Int,
    override val name: String = "Blood Pressure",
    override val internalName: String = "blood-pressure",
    override val description: String = "The pressure of blood in the circulatory system.",
    override val id: String = "loinc:8480-6", // LOINC code for blood pressure measurement,
    override val timestamp: Long = System.currentTimeMillis(),

) : Property, Instant {

    companion object: Default<BloodPressure> {

        override fun defaultValue(): BloodPressure {
            return BloodPressure(120, 80)
        }
    }
}