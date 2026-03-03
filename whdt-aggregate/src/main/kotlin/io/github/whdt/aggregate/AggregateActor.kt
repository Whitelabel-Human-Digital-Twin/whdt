package io.github.whdt.aggregate

import io.github.whdt.aggregate.event.DtaEvent
import io.github.whdt.aggregate.event.EventBus

interface AggregateActor {
    val eventFilter: (DtaEvent) -> Boolean
    suspend fun receive(e: DtaEvent)

    suspend fun run() {
        EventBus.collectEventsWithFilter(eventFilter) {
            receive(it)
        }
    }
}