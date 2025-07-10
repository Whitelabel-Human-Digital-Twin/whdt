package io.github.lm98.whdt.core.hdt

import io.github.lm98.whdt.core.hdt.interfaces.digital.DigitalInterface
import io.github.lm98.whdt.core.hdt.interfaces.physical.PhysicalInterface
import io.github.lm98.whdt.core.hdt.model.Model
import kotlinx.serialization.Serializable

@Serializable
data class HumanDigitalTwin(
    val id: String,
    val models: List<Model> = emptyList(),
    val physicalInterfaces: List<PhysicalInterface> = emptyList(),
    val digitalInterfaces: List<DigitalInterface> = emptyList(),
)