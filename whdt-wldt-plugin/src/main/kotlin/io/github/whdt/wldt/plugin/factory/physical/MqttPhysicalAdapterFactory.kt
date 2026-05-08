package io.github.whdt.wldt.plugin.factory.physical

import io.github.whdt.core.hdt.interfaces.physical.PhysicalInterface
import io.github.whdt.core.hdt.interfaces.physical.PhysicalInterfaceType
import io.github.whdt.core.hdt.model.property.Property
import io.github.whdt.distributed.namespace.Namespace
import io.github.whdt.distributed.serde.SerDe
import it.wldt.adapter.mqtt.physical.MqttPhysicalAdapter
import it.wldt.adapter.mqtt.physical.MqttPhysicalAdapterConfiguration

class MqttPhysicalAdapterFactory(
    private val propertySerDe: SerDe<Property>,
) : PhysicalAdapterFactory {
    override val interfaceType = PhysicalInterfaceType.MQTT

    override fun validate(pI: PhysicalInterface): Result<Unit> = runCatching {
        pI.optionalString("broker", DEFAULT_BROKER)
        pI.optionalInt("port", DEFAULT_PORT)
    }

    override fun create(pI: PhysicalInterface, properties: List<Property>): MqttPhysicalAdapter {
        val broker = pI.optionalString("broker", DEFAULT_BROKER)
        val port = pI.optionalInt("port", DEFAULT_PORT)
        val builder = MqttPhysicalAdapterConfiguration.builder(broker, port)
        properties.forEach { property ->
            builder.addPhysicalAssetPropertyAndTopic(
                property.id.toString(),
                property,
                Namespace.propertyUpdateRequestTopic(pI.hdtId, property.name)
            ) { string ->
                propertySerDe.deserialize(string)
            }
        }
        return MqttPhysicalAdapter(pI.id.toString(), builder.build())
    }

    private companion object {
        const val DEFAULT_BROKER = "localhost"
        const val DEFAULT_PORT = 1883
    }
}
