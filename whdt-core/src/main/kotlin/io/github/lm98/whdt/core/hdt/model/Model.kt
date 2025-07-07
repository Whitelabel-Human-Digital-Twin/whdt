package io.github.lm98.whdt.core.hdt.model

import io.github.lm98.whdt.core.hdt.model.property.Property

interface Model {
    val properties: List<Property>
}

class ModelImpl(
    override val properties: List<Property>
) : Model
