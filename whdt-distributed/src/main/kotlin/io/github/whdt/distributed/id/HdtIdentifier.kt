package io.github.whdt.distributed.id

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
data class HdtIdentifier(val id: String, val qualifier: String) {
    companion object {

        @OptIn(ExperimentalUuidApi::class)
        fun fromQualifier(qualifier: String): HdtIdentifier {
            return HdtIdentifier(
                id = "hdt-$qualifier-${Uuid.random()}",
                qualifier = qualifier
            )
        }

        fun newId(id: String): HdtIdentifier {
            return HdtIdentifier(id, id)
        }
    }
}
