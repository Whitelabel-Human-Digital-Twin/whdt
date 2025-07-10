package io.github.lm98.whdt.core.hdt.model.property

import io.github.lm98.whdt.core.util.Default
import io.github.lm98.whdt.core.util.Instant
import kotlinx.serialization.Serializable

@Serializable
data class BloodOxygen(
    val percentage: Int,
    override val name: String = "Blood Oxygen",
    override val internalName: String = "blood-oxygen",
    override val description: String = "The level of oxygen in the blood.",
    override val id: String = "loinc:2708-6", // LOINC code for oxygen saturation measurement
    override val timestamp: Long = System.currentTimeMillis(),
    val timezone: Int = 0,
) : Property, Instant {

    companion object: Default<BloodOxygen> {

        override fun defaultValue(): BloodOxygen {
            return BloodOxygen(95)
        }
    }
}