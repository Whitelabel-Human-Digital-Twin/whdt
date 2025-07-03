package execution

import hdt.HumanDigitalTwin

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
    fun run()
}