package hdt.model

import hdt.model.property.Property

interface Model {
    val properties: List<Property>
}

class ModelImpl(
    override val properties: List<Property>
) : Model
