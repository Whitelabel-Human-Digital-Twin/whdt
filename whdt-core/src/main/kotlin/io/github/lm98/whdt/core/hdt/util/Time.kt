package io.github.lm98.whdt.core.hdt.util

interface Instant {
    val timestamp: Long
}

interface TimePeriod {
    val startTime: Long
    val endTime: Long
}