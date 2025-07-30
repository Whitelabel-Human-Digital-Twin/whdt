package io.github.lm98.whdt.core.hdt.model.property

import io.github.lm98.whdt.core.util.Default
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("given-name")
data class GivenName(
    val givenName: String,
    override val name: String = "Given Name",
    override val internalName: String = name.lowercase().replace(" ", "-"),
    override val description: String = "A name property.",
    override val id: String = "custom:given-name",
) : Property {

    companion object: Default<GivenName> {

        override fun defaultValue(): GivenName {
            return GivenName("Default Name")
        }
    }
}