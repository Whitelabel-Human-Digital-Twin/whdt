package io.github.lm98.whdt.core.util

interface Serialize {
    fun serialize(s: Serialize): String
}