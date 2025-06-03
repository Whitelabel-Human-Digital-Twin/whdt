import execution.ExecutionStrategy
import hdt.HumanDigitalTwin
import it.wldt.core.engine.DigitalTwinEngine
import mapping.Util

object WLDTExecutionStrategy: ExecutionStrategy<Unit> {
    val dtEngine = DigitalTwinEngine()

    override fun execute(dts: List<HumanDigitalTwin>): Result<Unit> {
        dts
            .map { Util.getDtFromHumanDigitalTwin(it) }
            .forEach { dtEngine.addDigitalTwin(it) }
        dtEngine.startAll()
        return Result.success(Unit)
    }
}