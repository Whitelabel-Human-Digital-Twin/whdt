package io.github.lm98.whdt.core.hdt.model.property

import io.github.lm98.whdt.core.util.Deserialize
import io.github.lm98.whdt.core.util.Serialize
import kotlinx.serialization.Serializable

@Serializable
sealed interface Property: Deserialize, Serialize {
    val name: String
    val internalName: String
    val description: String
    val id: String
}