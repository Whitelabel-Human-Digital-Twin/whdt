package io.github.lm98.whdt.core.hdt

import io.github.lm98.whdt.core.hdt.interfaces.digital.DigitalInterface
import io.github.lm98.whdt.core.hdt.interfaces.physical.PhysicalInterface
import io.github.lm98.whdt.core.hdt.model.Model

interface HumanDigitalTwin {
    val id: String
    val models: List<Model>
    val physicalInterfaces: List<PhysicalInterface>
    val digitalInterfaces: List<DigitalInterface>
}

class HumanDigitalTwinImpl(
    override val id: String,
    override val models: List<Model> = emptyList(),
    override val physicalInterfaces: List<PhysicalInterface> = emptyList(),
    override val digitalInterfaces: List<DigitalInterface> = emptyList(),
) : HumanDigitalTwin