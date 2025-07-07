package io.github.lm98.whdt.core.hdt.util

interface Serialize {
    fun serialize(s: Serialize): String
}