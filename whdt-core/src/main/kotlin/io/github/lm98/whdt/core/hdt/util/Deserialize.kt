package io.github.lm98.whdt.core.hdt.util

interface Deserialize {
    fun deserialize(value: String): Deserialize
}