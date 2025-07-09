package io.github.lm98.whdt.core.util

interface Deserialize {
    fun deserialize(value: String): Deserialize
}