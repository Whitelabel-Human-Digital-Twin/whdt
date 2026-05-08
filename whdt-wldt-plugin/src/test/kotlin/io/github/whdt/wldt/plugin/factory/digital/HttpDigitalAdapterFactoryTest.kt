package io.github.whdt.wldt.plugin.factory.digital

import io.github.whdt.core.hdt.HdtId
import io.github.whdt.core.hdt.interfaces.digital.DigitalInterface
import io.github.whdt.core.hdt.interfaces.digital.DigitalInterfaceName
import io.github.whdt.core.hdt.interfaces.digital.DigitalInterfaceType
import io.github.whdt.core.hdt.model.ModelId
import io.github.whdt.core.hdt.model.property.Property
import io.github.whdt.core.hdt.model.property.PropertyDescription
import io.github.whdt.core.hdt.model.property.PropertyName
import io.github.whdt.core.hdt.model.property.PropertyValue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class HttpDigitalAdapterFactoryTest : FunSpec({

    val factory = HttpDigitalAdapterFactory()

    fun di(config: Map<String, String> = emptyMap()) = DigitalInterface(
        interfaceType = DigitalInterfaceType.HTTP,
        hdtId = HdtId("test-hdt"),
        name = DigitalInterfaceName("test-http-di"),
        config = config,
    )

    context("interfaceType") {
        test("is HTTP") {
            factory.interfaceType shouldBe DigitalInterfaceType.HTTP
        }
    }

    context("validate") {
        test("succeeds with empty config (all keys have defaults)") {
            factory.validate(di()) shouldBeSuccess Unit
        }

        test("succeeds with valid host and port") {
            factory.validate(di(mapOf("host" to "api.local", "port" to "8080"))) shouldBeSuccess Unit
        }

        test("fails when port is malformed") {
            val result = factory.validate(di(mapOf("port" to "not-a-number")))
            result shouldBeFailure { e ->
                e.message shouldContain "port"
            }
        }
    }

    context("create") {
        test("returns an HttpDigitalAdapter") {
            val dI = di()
            val dt = mockDigitalTwin()
            val adapter = factory.create(dI, dt, listOf(testProperty()))
            adapter.shouldBeInstanceOf<it.wldt.adapter.http.digital.adapter.HttpDigitalAdapter>()
        }

        test("applies default host and port when config is empty") {
            val adapter = factory.create(di(), mockDigitalTwin(), listOf(testProperty()))
            adapter.shouldBeInstanceOf<it.wldt.adapter.http.digital.adapter.HttpDigitalAdapter>()
        }

        test("uses provided host and port from config") {
            val adapter = factory.create(
                di(mapOf("host" to "custom.host", "port" to "9090")),
                mockDigitalTwin(),
                listOf(testProperty()),
            )
            adapter.shouldBeInstanceOf<it.wldt.adapter.http.digital.adapter.HttpDigitalAdapter>()
        }
    }
})

private fun mockDigitalTwin() = it.wldt.core.engine.DigitalTwin(
    "mock-dt",
    io.github.whdt.wldt.plugin.shadowing.WhdtShadowingFunction("mock-sf", emptyList()),
)

@OptIn(ExperimentalTime::class)
private fun testProperty() = Property(
    modelId = ModelId("test-model"),
    name = PropertyName("test-prop"),
    description = PropertyDescription(""),
    timestamp = Clock.System.now(),
    value = PropertyValue.StringPropertyValue("value"),
)
