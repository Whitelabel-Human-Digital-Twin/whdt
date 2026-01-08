package io.github.whdt.core.hdt

import io.github.whdt.core.hdt.interfaces.digital.DigitalInterface
import io.github.whdt.core.hdt.interfaces.physical.PhysicalInterface
import io.github.whdt.core.hdt.model.Model
import io.github.whdt.core.hdt.storage.Storage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a Human Digital Twin (HDT), a digital representation of a human being.
 */
@Serializable
@SerialName("human-digital-twin")
data class HumanDigitalTwin(
    val id: String,
    val models: List<Model> = emptyList(),
    val physicalInterfaces: List<PhysicalInterface> = emptyList(),
    val digitalInterfaces: List<DigitalInterface> = emptyList(),
    val storages: List<Storage> = listOf(Storage.default()),
)
