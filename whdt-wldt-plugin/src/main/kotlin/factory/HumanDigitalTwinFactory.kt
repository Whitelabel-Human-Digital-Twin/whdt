package factory

import hdt.HumanDigitalTwin
import hdt.interfaces.digital.MqttDigitalInterface
import hdt.interfaces.physical.MqttPhysicalInterface
import it.wldt.adapter.mqtt.digital.MqttDigitalAdapter
import it.wldt.adapter.mqtt.digital.MqttDigitalAdapterConfiguration
import it.wldt.adapter.mqtt.digital.topic.MqttQosLevel
import it.wldt.adapter.mqtt.physical.MqttPhysicalAdapter
import it.wldt.core.engine.DigitalTwin
import shadowing.HdtShadowingFunction

object HumanDigitalTwinFactory {
    fun fromHumanDigitalTwin(hdt: HumanDigitalTwin): DigitalTwin {
        val shad = HdtShadowingFunction("${hdt.id}-shadowing-function", hdt.models)
        val dt = DigitalTwin(hdt.id, shad)

        val physicalAdapters = hdt.physicalInterfaces.mapNotNull { pI ->
            when (pI) {
                is MqttPhysicalInterface -> getPaFromPhysicalInterfaceMqtt(pI)
                else -> null // Handle other physical interfaces if needed
            }
        }

        physicalAdapters.forEach { dt.addPhysicalAdapter(it) }

        val digitalAdapters = hdt.digitalInterfaces.mapNotNull { dI ->
            when (dI) {
                is MqttDigitalInterface -> getDaFromDigitalInterfaceMqtt(dI)
                else -> null // Handle other digital interfaces if needed
            }
        }

        digitalAdapters.forEach { dt.addDigitalAdapter(it) }

        return dt
    }

    fun getPaFromPhysicalInterfaceMqtt(pI: MqttPhysicalInterface): MqttPhysicalAdapter {
        val mqttConfigBuilder = it.wldt.adapter.mqtt.physical.MqttPhysicalAdapterConfiguration.builder(
            pI.broker,
            pI.port,
        )

        pI.properties.forEach { property ->
            mqttConfigBuilder.addPhysicalAssetPropertyAndTopic(
                property.internalName,
                property,
                "${pI.clientId}/sensor/${property.internalName}",
                property::deserialize
            )
        }

        val mqttConfig = mqttConfigBuilder.build()

        return MqttPhysicalAdapter(
            pI.clientId,
            mqttConfig
        )
    }

    fun getDaFromDigitalInterfaceMqtt(dI: MqttDigitalInterface): MqttDigitalAdapter {
        val mqttConfigBuilder = MqttDigitalAdapterConfiguration.builder(
            dI.broker,
            dI.port,
        )

        dI.properties.forEach { property ->
            mqttConfigBuilder.addPropertyTopic(
                property.internalName,
                "${dI.clientId}/state/${property.internalName}",
                MqttQosLevel.MQTT_QOS_0,
                property::serialize
            )
        }

        val mqttConfig = mqttConfigBuilder.build()

        return MqttDigitalAdapter(
            dI.clientId,
            mqttConfig
        )
    }
}