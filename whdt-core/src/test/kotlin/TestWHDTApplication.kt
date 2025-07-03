import execution.WHDTApplication
import hdt.HumanDigitalTwin

object ExecutionStrategyImpl: ExecutionStrategy<Unit> {
    override fun execute(dts: List<HumanDigitalTwin>): Result<Unit> {
        println("Executing with ${dts.size} digital twins")
        return Result.success(Unit)
    }
}

class TestWHDTApplication: WHDTApplication<Unit> {
    override fun run(executionStrategy: ExecutionStrategy<Unit>): Result<Unit> {
        val dts: List<HumanDigitalTwin> = emptyList()
        return executionStrategy.execute(dts)
    }
}

fun main() {
    val app = TestWHDTApplication()
    app.run(ExecutionStrategyImpl)
}