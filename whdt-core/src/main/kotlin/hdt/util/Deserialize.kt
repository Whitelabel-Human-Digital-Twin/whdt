package hdt.util

interface Deserialize {
    fun deserialize(value: String): Deserialize
}