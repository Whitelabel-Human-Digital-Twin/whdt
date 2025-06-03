import execution.ExecutionStrategy
import execution.WHDTApplication
import hdt.HumanDigitalTwin

class App(val hdts: List<HumanDigitalTwin>): WHDTApplication<Unit> {
    override fun run(executionStrategy: ExecutionStrategy<Unit>): Result<Unit> {
        return executionStrategy.execute(hdts)
    }
}

fun main() {
    val hdts: List<HumanDigitalTwin> = emptyList()

    App(hdts).run(WLDTExecutionStrategy)
}