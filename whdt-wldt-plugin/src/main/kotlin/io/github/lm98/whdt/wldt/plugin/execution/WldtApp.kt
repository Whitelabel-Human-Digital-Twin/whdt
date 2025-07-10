package io.github.lm98.whdt.wldt.plugin.execution

import io.github.lm98.whdt.wldt.plugin.factory.HumanDigitalTwinFactory.fromHumanDigitalTwin
import io.github.lm98.whdt.core.hdt.HumanDigitalTwin
import io.github.lm98.whdt.core.execution.App
import it.wldt.core.engine.DigitalTwinEngine

class WldtApp: App {
    private val dtEngine: DigitalTwinEngine = DigitalTwinEngine()

    override fun addDt(hdt: HumanDigitalTwin): App {
        dtEngine.addDigitalTwin(fromHumanDigitalTwin(hdt))
        return this
    }

    override fun addDts(hdts: List<HumanDigitalTwin>): App {
        hdts.forEach {
            dtEngine.addDigitalTwin(fromHumanDigitalTwin(it))
        }
        return this
    }

    override fun removeDtById(id: String): App {
        dtEngine.removeDigitalTwin(id)
        return this
    }

    override fun removeDtsById(ids: List<String>): App {
        ids.forEach { dtEngine.removeDigitalTwin(it) }
        return this
    }

    override fun run() {
        dtEngine.startAll()
    }
}