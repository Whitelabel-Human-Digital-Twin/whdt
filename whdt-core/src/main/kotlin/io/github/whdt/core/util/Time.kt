package io.github.whdt.core.util

interface Instant {
    val timestamp: Long
}

interface TimePeriod {
    val startTime: Long
    val endTime: Long
}