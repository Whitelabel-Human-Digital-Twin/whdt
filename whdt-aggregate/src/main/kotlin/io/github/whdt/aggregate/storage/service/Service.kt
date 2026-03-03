package io.github.whdt.aggregate.storage.service

import io.github.whdt.aggregate.storage.record.Record
import java.util.*

interface Service<R: Record> {
    suspend fun insert(record: R): Result<String>
    suspend fun get(id: String): Optional<R>
    suspend fun delete(id: String): Result<Unit>
    suspend fun update(id: String, record: R): Result<Unit>
    suspend fun getAll(): Result<List<R>>
}