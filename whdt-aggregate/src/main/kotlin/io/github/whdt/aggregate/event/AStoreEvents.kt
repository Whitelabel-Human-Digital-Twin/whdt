package io.github.whdt.aggregate.event

sealed class AStoreEvents : DtaEvent {
    data class StateRequest(val _a: Unit): AStoreEvents()
}