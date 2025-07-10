package io.github.lm98.whdt.core.hdt.model.property

import kotlinx.serialization.Serializable

@Serializable
sealed interface Property {
    val name: String
    val internalName: String
    val description: String
    val id: String
}