package hdt

import hdt.model.Property

interface Model {
    val properties: List<Property>
}

interface HumanDigitalTwin {
    val id: String
    val models: List<Model>
}

class ModelImpl(
    override val properties: List<Property>
) : Model

class HumanDigitalTwinImpl(
    override val id: String,
    override val models: List<Model>
) : HumanDigitalTwin