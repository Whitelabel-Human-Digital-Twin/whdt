package io.github.whdt.core.serde

import io.github.whdt.core.hdt.interfaces.digital.DigitalInterface
import io.github.whdt.core.hdt.interfaces.digital.HttpDigitalInterface
import io.github.whdt.core.hdt.interfaces.digital.MqttDigitalInterface
import io.github.whdt.core.hdt.interfaces.physical.MqttPhysicalInterface
import io.github.whdt.core.hdt.interfaces.physical.PhysicalInterface
import io.github.whdt.core.hdt.model.property.Properties.singleValueProperty
import io.github.whdt.core.hdt.model.property.PropertyValue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class TestSerDeInterfaces: FunSpec({
    test("Test SerDe Physical Interfaces") {
        val serde = Stub.physicalInterfaceJsonSerDe()
        val pI: PhysicalInterface = MqttPhysicalInterface(
            clientId = "mqtt-pi",
            properties = listOf(
                singleValueProperty("username", "username", value = PropertyValue.StringPropertyValue("leona")),
                singleValueProperty("password", "password", value = PropertyValue.StringPropertyValue("123456")),
            )
        )
        val serialized = serde.serialize(pI)
        //println(serialized)
        val deserialized = serde.deserialize(serialized)

        deserialized shouldBe pI
    }

    test("Test SerDe Digital Interfaces") {
        val serde = Stub.digitalInterfaceJsonSerDe()
        val properties = listOf(
            singleValueProperty("username", "username", value = PropertyValue.StringPropertyValue("leona")),
            singleValueProperty("password", "password", value = PropertyValue.StringPropertyValue("123456")),
        )

        val mqtt: DigitalInterface = MqttDigitalInterface(
            clientId = "mqtt-di",
            properties = properties
        )
        val serMqtt = serde.serialize(mqtt)
        //println(serialized)
        val deMqtt = serde.deserialize(serMqtt)
        deMqtt shouldBe mqtt

        val http: DigitalInterface = HttpDigitalInterface(id = "http-di", properties = properties)
        val serHttp = serde.serialize(http)
        //println(serialized)
        val deHttp = serde.deserialize(serHttp)
        deHttp shouldBe http
    }
})