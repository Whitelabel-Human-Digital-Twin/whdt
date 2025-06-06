package execution

import hdt.HumanDigitalTwin

interface WHDTApplication<T> {
    fun run(executionStrategy: ExecutionStrategy<T>): Result<T>
}

class App(val hdts: List<HumanDigitalTwin>): WHDTApplication<Unit> {
    override fun run(executionStrategy: ExecutionStrategy<Unit>): Result<Unit> {
        return executionStrategy.execute(hdts)
    }
}