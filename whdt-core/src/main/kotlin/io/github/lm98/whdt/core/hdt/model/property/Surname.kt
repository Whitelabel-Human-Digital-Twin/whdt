package io.github.lm98.whdt.core.hdt.model.property

import io.github.lm98.whdt.core.util.Default
import kotlinx.serialization.Serializable

@Serializable
data class Surname(
    val surname: String,
    override val name: String = "Surname",
    override val internalName: String = name.lowercase().replace(" ", "-"),
    override val description: String = "A name property.",
    override val id: String = "custom:surname",
) : Property {

    companion object: Default<Surname> {

        override fun defaultValue(): Surname {
            return Surname("Default Name")
        }
    }
}