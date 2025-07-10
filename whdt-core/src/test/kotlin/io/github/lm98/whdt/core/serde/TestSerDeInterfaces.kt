package io.github.lm98.whdt.core.serde

import io.github.lm98.whdt.core.hdt.interfaces.digital.DigitalInterface
import io.github.lm98.whdt.core.hdt.interfaces.digital.HttpDigitalInterface
import io.github.lm98.whdt.core.hdt.interfaces.digital.MqttDigitalInterface
import io.github.lm98.whdt.core.hdt.interfaces.physical.MqttPhysicalInterface
import io.github.lm98.whdt.core.hdt.interfaces.physical.PhysicalInterface
import io.github.lm98.whdt.core.hdt.model.property.GenericProperty
import io.github.lm98.whdt.core.hdt.model.property.PropertyValue
import io.github.lm98.whdt.core.serde.modules.interfaceModule
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.json.Json

class TestSerDeInterfaces: FunSpec({
    test("Test SerDe Physical Interfaces") {
        val json = Json {
            serializersModule = interfaceModule
            classDiscriminator = "type"
            prettyPrint = true
        }

        val pI: PhysicalInterface = MqttPhysicalInterface(
            properties = listOf(
                GenericProperty("username", "username", value = PropertyValue.StringPropertyValue("leona")),
                GenericProperty("password", "password", value = PropertyValue.StringPropertyValue("123456")),
            )
        )
        val serialized = json.encodeToString(pI)
        //println(serialized)
        val deserialized = json.decodeFromString<PhysicalInterface>(serialized)

        deserialized shouldBe pI
    }

    test("Test SerDe Digital Interfaces") {
        val json = Json {
            serializersModule = interfaceModule
            classDiscriminator = "type"
            prettyPrint = true
        }
        val properties = listOf(
            GenericProperty("username", "username", value = PropertyValue.StringPropertyValue("leona")),
            GenericProperty("password", "password", value = PropertyValue.StringPropertyValue("123456")),
        )

        val mqtt: DigitalInterface = MqttDigitalInterface(properties)
        val serMqtt = json.encodeToString(mqtt)
        //println(serialized)
        val deMqtt = json.decodeFromString<DigitalInterface>(serMqtt)
        deMqtt shouldBe mqtt

        val http: DigitalInterface = HttpDigitalInterface(properties)
        val serHttp = json.encodeToString(http)
        //println(serialized)
        val deHttp = json.decodeFromString<DigitalInterface>(serHttp)
        deHttp shouldBe http
    }
})