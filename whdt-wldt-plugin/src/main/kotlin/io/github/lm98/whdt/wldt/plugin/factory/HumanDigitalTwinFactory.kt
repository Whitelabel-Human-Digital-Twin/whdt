package io.github.lm98.whdt.wldt.plugin.factory

import io.github.lm98.whdt.core.hdt.HumanDigitalTwin
import io.github.lm98.whdt.core.hdt.interfaces.digital.HttpDigitalInterface
import io.github.lm98.whdt.core.hdt.interfaces.digital.MqttDigitalInterface
import io.github.lm98.whdt.core.hdt.interfaces.physical.MqttPhysicalInterface
import io.github.lm98.whdt.core.hdt.model.property.Property
import io.github.lm98.whdt.core.serde.modules.propertyModule
import io.github.lm98.whdt.wldt.plugin.shadowing.HdtShadowingFunction
import it.wldt.adapter.http.digital.adapter.HttpDigitalAdapter
import it.wldt.adapter.http.digital.adapter.HttpDigitalAdapterConfiguration
import it.wldt.adapter.mqtt.digital.MqttDigitalAdapter
import it.wldt.adapter.mqtt.digital.MqttDigitalAdapterConfiguration
import it.wldt.adapter.mqtt.digital.topic.MqttQosLevel
import it.wldt.adapter.mqtt.physical.MqttPhysicalAdapter
import it.wldt.adapter.mqtt.physical.MqttPhysicalAdapterConfiguration
import it.wldt.core.engine.DigitalTwin
import kotlinx.serialization.json.Json

object HumanDigitalTwinFactory {
    val json = Json {
        serializersModule = propertyModule
        classDiscriminator = "type"
        prettyPrint = true
    }

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
                is HttpDigitalInterface -> getDaFromHttpDigitalInterface(dI, dt)
                else -> null // Handle other digital interfaces if needed
            }
        }

        digitalAdapters.forEach { dt.addDigitalAdapter(it) }

        return dt
    }

    fun getPaFromPhysicalInterfaceMqtt(pI: MqttPhysicalInterface): MqttPhysicalAdapter {
        val mqttConfigBuilder = MqttPhysicalAdapterConfiguration.builder(
            pI.broker,
            pI.port,
        )

        pI.properties.forEach { property ->
            mqttConfigBuilder.addPhysicalAssetPropertyAndTopic(
                property.internalName,
                property,
                "${pI.clientId}/sensor/${property.internalName}",
                json::decodeFromString
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
                MqttQosLevel.MQTT_QOS_0
            ) { property: Property ->
                json.encodeToString(property)
            }
        }

        val mqttConfig = mqttConfigBuilder.build()

        return MqttDigitalAdapter(
            dI.clientId,
            mqttConfig
        )
    }

    fun getDaFromHttpDigitalInterface(dI: HttpDigitalInterface, dt: DigitalTwin): HttpDigitalAdapter {
        val httpConfig  = HttpDigitalAdapterConfiguration(dI.id, dI.host, dI.port)

        httpConfig.addPropertiesFilter(dI.properties.map { it.internalName })

        return HttpDigitalAdapter(
            httpConfig,
            dt
        )
    }
}