package io.github.whdt.wldt.plugin.factory

import io.github.whdt.core.hdt.HumanDigitalTwin
import io.github.whdt.core.hdt.interfaces.digital.DigitalInterface
import io.github.whdt.core.hdt.interfaces.digital.DigitalInterfaceType
import io.github.whdt.core.hdt.interfaces.physical.PhysicalInterface
import io.github.whdt.core.hdt.interfaces.physical.PhysicalInterfaceType
import io.github.whdt.core.hdt.model.property.Property
import io.github.whdt.core.hdt.storage.StorageType
import io.github.whdt.distributed.namespace.Namespace
import io.github.whdt.distributed.serde.Stub
import io.github.whdt.wldt.plugin.shadowing.WhdtShadowingFunction
import it.wldt.adapter.digital.DigitalAdapter
import it.wldt.adapter.http.digital.adapter.HttpDigitalAdapter
import it.wldt.adapter.http.digital.adapter.HttpDigitalAdapterConfiguration
import it.wldt.adapter.mqtt.digital.MqttDigitalAdapter
import it.wldt.adapter.mqtt.digital.MqttDigitalAdapterConfiguration
import it.wldt.adapter.mqtt.digital.topic.MqttQosLevel
import it.wldt.adapter.mqtt.physical.MqttPhysicalAdapter
import it.wldt.adapter.mqtt.physical.MqttPhysicalAdapterConfiguration
import it.wldt.adapter.physical.PhysicalAdapter
import it.wldt.core.engine.DigitalTwin
import it.wldt.storage.DefaultWldtStorage
import java.util.logging.Logger
import kotlin.time.ExperimentalTime

object HumanDigitalTwinFactory {
    val logger: Logger = Logger.getLogger("HumanDigitalTwinFactory")
    val propertySerDe = Stub.propertyJsonSerDe()

    fun fromHumanDigitalTwin(hdt: HumanDigitalTwin): DigitalTwin {

        val shad = WhdtShadowingFunction("${hdt.hdtId}-shadowing-function", hdt.models)
        val dt = DigitalTwin(hdt.hdtId.id, shad)

        val properties = hdt.models.flatMap { it.properties }

        hdt.physicalInterfaces.forEach { pI ->
            val pa: PhysicalAdapter? = when (pI.interfaceType) {
                PhysicalInterfaceType.MQTT -> getPaFromPhysicalInterfaceMqtt(pI, properties)
            }
            if (pa != null) dt.addPhysicalAdapter(pa)
        }

        hdt.digitalInterfaces.forEach { dI ->
            val da: DigitalAdapter<*>? = when (dI.interfaceType) {
                DigitalInterfaceType.MQTT -> getDaFromDigitalInterfaceMqtt(dI, properties)
                DigitalInterfaceType.HTTP -> getDaFromHttpDigitalInterface(dI, dt, properties)
            }
            if (da != null) dt.addDigitalAdapter(da)
        }

        val storages = hdt.storages.map { storage ->
            when (storage.storageType) {
                StorageType.IN_MEMORY -> DefaultWldtStorage("${hdt.hdtId}-default-storage", true)
                else -> DefaultWldtStorage("${hdt.hdtId}-default-storage", true)
            }
        }

        storages.forEach { dt.storageManager.putStorage(it) }

        return dt
    }

    fun getPaFromPhysicalInterfaceMqtt(pI: PhysicalInterface, properties: List<Property>): MqttPhysicalAdapter {
        val broker = pI.optionalString("broker", "localhost")
        val port = pI.optionalInt("port", 1883)
        val mqttConfigBuilder = MqttPhysicalAdapterConfiguration.builder(broker, port)

        properties.forEach { property ->
            mqttConfigBuilder.addPhysicalAssetPropertyAndTopic(
                property.id.toString(),
                property,
                Namespace.propertyUpdateRequestTopic(pI.hdtId, property.name)
            ) { string ->
                propertySerDe.deserialize(string)
            }
        }

        return MqttPhysicalAdapter(pI.id.toString(), mqttConfigBuilder.build())
    }

    @OptIn(ExperimentalTime::class)
    fun getDaFromDigitalInterfaceMqtt(dI: DigitalInterface, properties: List<Property>): MqttDigitalAdapter {
        val broker = dI.optionalString("broker", "localhost")
        val port = dI.optionalInt("port", 1883)
        val mqttConfigBuilder = MqttDigitalAdapterConfiguration.builder(broker, port)

        properties.forEach { property ->
            mqttConfigBuilder.addPropertyTopic(
                property.id.toString(),
                Namespace.propertyUpdateNotificationTopic(dI.hdtId, property.name),
                MqttQosLevel.MQTT_QOS_0
            ) { p: Property ->
                propertySerDe.serialize(p)
            }
        }

        return MqttDigitalAdapter(dI.id.toString(), mqttConfigBuilder.build())
    }

    fun getDaFromHttpDigitalInterface(dI: DigitalInterface, dt: DigitalTwin, properties: List<Property>): HttpDigitalAdapter {
        val host = dI.optionalString("host", "localhost")
        val port = dI.optionalInt("port", 8080)
        val httpConfig = HttpDigitalAdapterConfiguration(dI.id.toString(), host, port)

        httpConfig.addPropertiesFilter(properties.map { it.id.toString() })

        return HttpDigitalAdapter(httpConfig, dt)
    }
}
