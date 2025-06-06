package hdt.util

interface Instant {
    val timestamp: Long
}

interface TimePeriod {
    val startTime: Long
    val endTime: Long
}