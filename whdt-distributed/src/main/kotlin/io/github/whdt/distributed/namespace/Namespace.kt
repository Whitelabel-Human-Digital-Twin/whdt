package io.github.whdt.distributed.namespace

object Namespace {
    const val PROPERTY_UPDATE_NOTIFICATION_TOPIC_MQTT = "whdt-property-update-notification"
    const val PROPERTY_UPDATE_REQUEST_TOPIC_MQTT = "whdt-property-update-request"

    val namespaces = listOf(
        PROPERTY_UPDATE_NOTIFICATION_TOPIC_MQTT,
        PROPERTY_UPDATE_REQUEST_TOPIC_MQTT,
        )

    fun verifyNamespace(namespace: String): Result<Unit> {
        return if (namespaces.contains(namespace)) {
            Result.success(Unit)
        } else {
            Result.failure(IllegalArgumentException("Namespace $namespace does not exist."))
        }
    }
}