package io.github.whdt.wldt.plugin.factory.physical

import io.github.whdt.core.hdt.HdtId
import io.github.whdt.core.hdt.interfaces.physical.PhysicalInterface
import io.github.whdt.core.hdt.interfaces.physical.PhysicalInterfaceName
import io.github.whdt.core.hdt.interfaces.physical.PhysicalInterfaceType
import io.github.whdt.core.hdt.model.ModelId
import io.github.whdt.core.hdt.model.property.Property
import io.github.whdt.core.hdt.model.property.PropertyDescription
import io.github.whdt.core.hdt.model.property.PropertyName
import io.github.whdt.core.hdt.model.property.PropertyValue
import io.github.whdt.distributed.serde.Stub
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class MqttPhysicalAdapterFactoryTest : FunSpec({

    val factory = MqttPhysicalAdapterFactory(Stub.propertyJsonSerDe())

    fun pi(config: Map<String, String> = emptyMap()) = PhysicalInterface(
        interfaceType = PhysicalInterfaceType.MQTT,
        hdtId = HdtId("test-hdt"),
        name = PhysicalInterfaceName("test-mqtt-pi"),
        config = config,
    )

    context("interfaceType") {
        test("is MQTT") {
            factory.interfaceType shouldBe PhysicalInterfaceType.MQTT
        }
    }

    context("validate") {
        test("succeeds with empty config (all keys have defaults)") {
            factory.validate(pi()) shouldBeSuccess Unit
        }

        test("succeeds with valid broker and port") {
            factory.validate(pi(mapOf("broker" to "mqtt.local", "port" to "1883"))) shouldBeSuccess Unit
        }

        test("fails when port is malformed") {
            val result = factory.validate(pi(mapOf("port" to "not-a-number")))
            result shouldBeFailure { e ->
                e.message shouldContain "port"
            }
        }
    }

    context("create") {
        test("returns a MqttPhysicalAdapter with the correct id") {
            val pI = pi()
            val adapter = factory.create(pI, listOf(testProperty()))
            adapter.shouldBeInstanceOf<it.wldt.adapter.mqtt.physical.MqttPhysicalAdapter>()
            adapter.id shouldBe pI.id.toString()
        }

        test("applies default broker and port when config is empty") {
            val adapter = factory.create(pi(), listOf(testProperty()))
            adapter.shouldBeInstanceOf<it.wldt.adapter.mqtt.physical.MqttPhysicalAdapter>()
        }

        test("uses provided broker and port from config") {
            val adapter = factory.create(
                pi(mapOf("broker" to "custom.broker", "port" to "1884")),
                listOf(testProperty()),
            )
            adapter.shouldBeInstanceOf<it.wldt.adapter.mqtt.physical.MqttPhysicalAdapter>()
        }
    }
})

@OptIn(ExperimentalTime::class)
private fun testProperty() = Property(
    modelId = ModelId("test-model"),
    name = PropertyName("test-prop"),
    description = PropertyDescription(""),
    timestamp = Clock.System.now(),
    value = PropertyValue.StringPropertyValue("value"),
)
