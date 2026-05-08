package io.github.whdt.wldt.plugin.factory.digital

import io.github.whdt.core.hdt.interfaces.digital.DigitalInterface
import io.github.whdt.core.hdt.interfaces.digital.DigitalInterfaceType
import io.github.whdt.core.hdt.model.property.Property
import io.github.whdt.distributed.namespace.Namespace
import io.github.whdt.distributed.serde.SerDe
import it.wldt.adapter.mqtt.digital.MqttDigitalAdapter
import it.wldt.adapter.mqtt.digital.MqttDigitalAdapterConfiguration
import it.wldt.adapter.mqtt.digital.topic.MqttQosLevel
import it.wldt.core.engine.DigitalTwin
import kotlin.time.ExperimentalTime

class MqttDigitalAdapterFactory(
    private val propertySerDe: SerDe<Property>,
) : DigitalAdapterFactory {
    override val interfaceType = DigitalInterfaceType.MQTT

    override fun validate(dI: DigitalInterface): Result<Unit> = runCatching {
        dI.optionalString("broker", DEFAULT_BROKER)
        dI.optionalInt("port", DEFAULT_PORT)
    }

    @OptIn(ExperimentalTime::class)
    override fun create(dI: DigitalInterface, dt: DigitalTwin, properties: List<Property>): MqttDigitalAdapter {
        val broker = dI.optionalString("broker", DEFAULT_BROKER)
        val port = dI.optionalInt("port", DEFAULT_PORT)
        val builder = MqttDigitalAdapterConfiguration.builder(broker, port)
        properties.forEach { property ->
            builder.addPropertyTopic(
                property.id.toString(),
                Namespace.propertyUpdateNotificationTopic(dI.hdtId, property.name),
                MqttQosLevel.MQTT_QOS_0
            ) { p: Property -> propertySerDe.serialize(p) }
        }
        return MqttDigitalAdapter(dI.id.toString(), builder.build())
    }

    private companion object {
        const val DEFAULT_BROKER = "localhost"
        const val DEFAULT_PORT = 1883
    }
}
