package io.github.whdt.aggregate.storage

import io.github.whdt.aggregate.AggregateActor
import io.github.whdt.aggregate.event.AStoreEvents
import io.github.whdt.aggregate.event.DtaEvent
import io.github.whdt.aggregate.storage.service.Service

class AggregateStoreManager: AggregateActor {
    override val eventFilter: (DtaEvent) -> Boolean = { event -> event is AStoreEvents }
    val services: MutableMap<String, Service<*>> = mutableMapOf()

    override suspend fun receive(e: DtaEvent) {

    }
}