package io.github.whdt.wldt.plugin.factory

import io.github.whdt.core.hdt.HumanDigitalTwin
import io.github.whdt.core.hdt.interfaces.digital.HttpDigitalInterface
import io.github.whdt.core.hdt.interfaces.digital.MqttDigitalInterface
import io.github.whdt.core.hdt.interfaces.physical.MqttPhysicalInterface
import io.github.whdt.core.hdt.model.property.Property
import io.github.whdt.core.hdt.storage.StorageType
import io.github.whdt.core.serde.Stub
import io.github.whdt.core.hdt.model.id.HdtId
import io.github.whdt.distributed.id.SenderId
import io.github.whdt.distributed.message.Message
import io.github.whdt.distributed.namespace.Namespace
import io.github.whdt.wldt.plugin.shadowing.HdtShadowingFunction
import it.wldt.adapter.http.digital.adapter.HttpDigitalAdapter
import it.wldt.adapter.http.digital.adapter.HttpDigitalAdapterConfiguration
import it.wldt.adapter.mqtt.digital.MqttDigitalAdapter
import it.wldt.adapter.mqtt.digital.MqttDigitalAdapterConfiguration
import it.wldt.adapter.mqtt.digital.topic.MqttQosLevel
import it.wldt.adapter.mqtt.physical.MqttPhysicalAdapter
import it.wldt.adapter.mqtt.physical.MqttPhysicalAdapterConfiguration
import it.wldt.core.engine.DigitalTwin
import it.wldt.storage.DefaultWldtStorage
import kotlinx.serialization.json.Json
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

object HumanDigitalTwinFactory {
    val serde = Stub.propertyJsonSerDe()
    fun fromHumanDigitalTwin(hdt: HumanDigitalTwin): DigitalTwin {

        val shad = HdtShadowingFunction("${hdt.id}-shadowing-function", hdt.models)
        val dt = DigitalTwin(hdt.id, shad)

        val properties = hdt.models.flatMap { it.properties }

        val physicalAdapters = hdt.physicalInterfaces.map { pI ->
            when (pI) {
                is MqttPhysicalInterface -> getPaFromPhysicalInterfaceMqtt(pI, properties)
                // Handle other physical interfaces if needed
            }
        }

        physicalAdapters.forEach { dt.addPhysicalAdapter(it) }

        val digitalAdapters = hdt.digitalInterfaces.map { dI ->
            when (dI) {
                is MqttDigitalInterface -> getDaFromDigitalInterfaceMqtt(dI, properties)
                is HttpDigitalInterface -> getDaFromHttpDigitalInterface(dI, dt, properties)
                // Handle other digital interfaces if needed
            }
        }

        digitalAdapters.forEach { dt.addDigitalAdapter(it) }

        val storages = hdt.storages.map { storage ->
            when(storage.storageType) {
                StorageType.IN_MEMORY -> DefaultWldtStorage("${hdt.id}-default-storage", true)
                else -> {
                    DefaultWldtStorage("${hdt.id}-default-storage", true)
                }
            }
        }

        storages.forEach { dt.storageManager.putStorage(it) }

        return dt
    }

    fun getPaFromPhysicalInterfaceMqtt(pI: MqttPhysicalInterface, properties: List<Property>): MqttPhysicalAdapter {
        val mqttConfigBuilder = MqttPhysicalAdapterConfiguration.builder(
            pI.broker,
            pI.port,
        )

        properties.forEach { property ->
            mqttConfigBuilder.addPhysicalAssetPropertyAndTopic(
                property.id,
                property,
                Namespace.PROPERTY_UPDATE_REQUEST_TOPIC_MQTT
            ) { string ->
                val message = Json.decodeFromString<Message>(string)
                val property = Json.decodeFromJsonElement(serde.serializer, message.payload)
                property
            }
        }

        val mqttConfig = mqttConfigBuilder.build()

        return MqttPhysicalAdapter(
            pI.clientId,
            mqttConfig
        )
    }

    @OptIn(ExperimentalTime::class)
    fun getDaFromDigitalInterfaceMqtt(dI: MqttDigitalInterface, properties: List<Property>): MqttDigitalAdapter {
        val mqttConfigBuilder = MqttDigitalAdapterConfiguration.builder(
            dI.broker,
            dI.port,
        )

        properties.forEach { property ->
            mqttConfigBuilder.addPropertyTopic(
                property.id,
                Namespace.PROPERTY_UPDATE_NOTIFICATION_TOPIC_MQTT,
                MqttQosLevel.MQTT_QOS_0
            ) { property: Property ->
                // Build a Message
                val message = Message(
                    hdt = HdtId.of(dI.clientId),
                    sender = SenderId.of(dI.clientId),
                    sentAt = Clock.System.now(),
                    payload = serde.serializeToJsonElement(property)
                )
                Json.encodeToString(message)
            }
        }

        val mqttConfig = mqttConfigBuilder.build()

        return MqttDigitalAdapter(
            dI.clientId,
            mqttConfig
        )
    }

    fun getDaFromHttpDigitalInterface(dI: HttpDigitalInterface, dt: DigitalTwin, properties: List<Property>): HttpDigitalAdapter {
        val httpConfig  = HttpDigitalAdapterConfiguration(dI.id, dI.host, dI.port)

        httpConfig.addPropertiesFilter(properties.map { it.id })

        return HttpDigitalAdapter(
            httpConfig,
            dt
        )
    }
}