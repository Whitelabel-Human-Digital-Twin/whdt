import execution.ExecutionStrategy
import hdt.HumanDigitalTwin
import it.wldt.core.engine.DigitalTwinEngine
import kotlin.collections.forEach

/**
 * WLDTExecutionStrategy is an implementation of ExecutionStrategy that sets up a WLDT Digital Twin Engine
 */
object WLDTExecutionStrategy: ExecutionStrategy<Unit> {
    private val dtEngine = DigitalTwinEngine()

    override fun execute(dts: List<HumanDigitalTwin>): Result<Unit> {
        dts
            .map { factory.HumanDigitalTwinFactory.fromHumanDigitalTwin(it) }
            .forEach { dtEngine.addDigitalTwin(it) }
        dtEngine.startAll()
        return Result.success(Unit)
    }
}