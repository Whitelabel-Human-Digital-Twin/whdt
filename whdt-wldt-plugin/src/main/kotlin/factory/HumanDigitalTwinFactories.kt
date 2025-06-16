package factory

import hdt.HumanDigitalTwin
import it.wldt.core.engine.DigitalTwin
import shadowing.HdtShadowingFunction

object HumanDigitalTwinFactories {
    fun fromHumanDigitalTwin(hdt: HumanDigitalTwin): DigitalTwin {
        val shad = HdtShadowingFunction("${hdt.id}-shadowing-function", hdt.models)
        return DigitalTwin(
            hdt.id,
            shad,
        )
    }
}