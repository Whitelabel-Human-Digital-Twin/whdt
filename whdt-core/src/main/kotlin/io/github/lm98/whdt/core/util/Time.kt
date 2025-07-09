package io.github.lm98.whdt.core.util

interface Instant {
    val timestamp: Long
}

interface TimePeriod {
    val startTime: Long
    val endTime: Long
}