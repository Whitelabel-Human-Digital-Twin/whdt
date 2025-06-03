package execution

interface WHDTApplication<T> {
    fun run(executionStrategy: ExecutionStrategy<T>): Result<T>
}