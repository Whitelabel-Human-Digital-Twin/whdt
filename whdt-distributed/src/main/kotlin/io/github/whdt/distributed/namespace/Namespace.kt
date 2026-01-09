package io.github.whdt.distributed.namespace

object Namespace {
    const val PROPERTY_TOPIC_MQTT = "whdt-property"

    val namespaces = listOf(
        PROPERTY_TOPIC_MQTT,
        )

    fun verifyNamespace(namespace: String): Result<Unit> {
        return if (namespaces.contains(namespace)) {
            Result.success(Unit)
        } else {
            Result.failure(IllegalArgumentException("Namespace $namespace does not exist."))
        }
    }
}