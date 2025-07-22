package io.github.lm98.whdt.core.execution

import io.github.lm98.whdt.core.hdt.HumanDigitalTwin

interface App {
    fun addDt(hdt: HumanDigitalTwin): Result<Unit>
    fun addDts(hdts: List<HumanDigitalTwin>): List<Result<Unit>> {
        return hdts.map { addDt(it) }
    }
    fun removeDtById(id: String): Result<Unit>
    fun removeDtsById(ids: List<String>): List<Result<Unit>> {
        return ids.map { removeDtById(it)  }
    }
    fun removeDt(hdt: HumanDigitalTwin): Result<Unit> {
        return removeDtById(hdt.id)
    }
    fun removeDts(hdts: List<HumanDigitalTwin>): List<Result<Unit>> {
        return removeDtsById(hdts.map { it.id })
    }
    fun startDt(id: String): Result<Unit>
    fun startDts(ids: List<String>): List<Result<Unit>> {
        return ids.map { startDt(it) }
    }
    fun stopDt(id: String): Result<Unit>
    fun stopDts(ids: List<String>): List<Result<Unit>> {
        return ids.map { stopDt(it) }
    }
    fun startAll(): Result<Unit>
    fun stopAll(): Result<Unit>
}