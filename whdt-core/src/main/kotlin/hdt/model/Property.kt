package hdt.model

import hdt.util.Default
import hdt.util.Deserialize
import hdt.util.Serialize

interface Property: Default, Deserialize, Serialize {
    val name: String
    val internalName: String
    val description: String
    val id: String
    val timestamp: Long
}