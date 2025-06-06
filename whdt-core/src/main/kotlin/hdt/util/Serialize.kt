package hdt.util

interface Serialize {
    fun serialize(s: Serialize): String
}