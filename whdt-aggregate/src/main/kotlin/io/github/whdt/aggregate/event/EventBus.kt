package io.github.whdt.aggregate.event

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filter

object EventBus {
    // Using SharedFlow for broadcasting events to multiple consumers
    private val _events = MutableSharedFlow<DtaEvent>(replay = 0) // No replay history
    val events: SharedFlow<DtaEvent> = _events.asSharedFlow()

    // Function to emit an event
    suspend fun emit(event: DtaEvent) {
        _events.emit(event)
    }

    // Function to collect events
    suspend fun collectEvents(action: suspend (DtaEvent) -> Unit) {
        events.collect { action(it) }
    }

    suspend fun collectEventsWithFilter(predicate: (DtaEvent) -> Boolean, action: suspend (DtaEvent) -> Unit) {
        events
            .filter(predicate)
            .collect { action(it) }
    }
}