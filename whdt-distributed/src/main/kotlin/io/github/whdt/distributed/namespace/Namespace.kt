package io.github.whdt.distributed.namespace

import io.github.whdt.distributed.id.HdtId

object Namespace {
    const val MQTT_PREFIX = "whdt"
    const val PROPERTY_UPDATE_NOTIFICATION_POSTFIX_MQTT = "property-update-notification"
    const val PROPERTY_UPDATE_REQUEST_POSTFIX_MQTT = "property-update-request"

    fun propertyUpdateRequestTopic(hdtId: HdtId) = "$MQTT_PREFIX/$hdtId/$PROPERTY_UPDATE_REQUEST_POSTFIX_MQTT"
    fun propertyUpdateNotificationTopic(hdtId: HdtId) = "$MQTT_PREFIX/$hdtId/$PROPERTY_UPDATE_NOTIFICATION_POSTFIX_MQTT"
}