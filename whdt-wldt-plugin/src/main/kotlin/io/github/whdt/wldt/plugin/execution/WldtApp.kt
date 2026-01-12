package io.github.whdt.wldt.plugin.execution

import io.github.whdt.wldt.plugin.factory.HumanDigitalTwinFactory.fromHumanDigitalTwin
import io.github.whdt.core.hdt.HumanDigitalTwin
import io.github.whdt.core.execution.App
import it.wldt.core.engine.DigitalTwinEngine

class WldtApp: App {
    private val dtEngine: DigitalTwinEngine = DigitalTwinEngine()

    override fun addDt(hdt: HumanDigitalTwin): Result<String> {
        return runCatching {
            dtEngine.addDigitalTwin(fromHumanDigitalTwin(hdt))
            hdt.hdtId.id
        }
    }

    override fun removeDtById(id: String): Result<Unit> {
        return runCatching { dtEngine.removeDigitalTwin(id) }
    }

    override fun startDt(id: String): Result<Unit> {
        return runCatching { dtEngine.startDigitalTwin(id) }
    }

    override fun stopDt(id: String): Result<Unit> {
        return runCatching { dtEngine.stopDigitalTwin(id) }
    }

    override fun startAll(): Result<Unit> {
        return runCatching { dtEngine.startAll() }
    }

    override fun stopAll(): Result<Unit> {
        return runCatching { dtEngine.stopAll() }
    }
}