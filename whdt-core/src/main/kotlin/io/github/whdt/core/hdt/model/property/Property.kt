package io.github.whdt.core.hdt.model.property

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant


@Serializable
@SerialName("property")
data class Property(
    val name: String,
    val id: String,
    val description: String,
    val timestamp: Instant,
    val value: PropertyValue,
)