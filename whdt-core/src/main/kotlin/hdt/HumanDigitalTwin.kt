package hdt

import hdt.interfaces.digital.DigitalInterface
import hdt.interfaces.physical.PhysicalInterface
import hdt.model.Model

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