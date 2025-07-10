package io.github.lm98.whdt.core.hdt.model.property

import io.github.lm98.whdt.core.util.Default
import io.github.lm98.whdt.core.util.TimePeriod
import kotlinx.serialization.Serializable

@Serializable
data class Steps(
    val count: Int,
    override val name: String = "Steps",
    override val internalName: String = "steps",
    override val description: String = "The number of steps taken.",
    override val id: String = "hdt:00002-1", // custom code for step count
    override val startTime: Long = System.currentTimeMillis(),
    override val endTime: Long = System.currentTimeMillis(),
): Property, TimePeriod {

    companion object: Default<Steps> {

        override fun defaultValue(): Steps {
            return Steps(0)
        }
    }
}