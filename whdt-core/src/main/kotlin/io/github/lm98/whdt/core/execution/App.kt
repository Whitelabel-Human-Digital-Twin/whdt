package io.github.lm98.whdt.core.execution

import io.github.lm98.whdt.core.hdt.HumanDigitalTwin

interface App {
    fun addDt(hdt: HumanDigitalTwin): App
    fun addDts(hdts: List<HumanDigitalTwin>): App
    fun removeDtById(id: String): App
    fun removeDtsById(ids: List<String>): App

    fun removeDt(hdt: HumanDigitalTwin): App {
        return removeDtById(hdt.id)
    }
    fun removeDts(hdts: List<HumanDigitalTwin>): App {
        return removeDtsById(hdts.map { it.id })
    }
    fun startDt(id: String): App
    fun startDts(ids: List<String>): App {
        ids.forEach { startDt(it) }
        return this
    }
    fun stopDt(id: String): App
    fun stopDts(ids: List<String>): App {
        ids.forEach { stopDt(it) }
        return this
    }
    fun startAll()
    fun stopAll()
}