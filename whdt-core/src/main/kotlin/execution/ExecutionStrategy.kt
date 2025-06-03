package execution

import hdt.HumanDigitalTwin

interface ExecutionStrategy<T> {
    fun execute(dts: List<HumanDigitalTwin>): Result<T>
}