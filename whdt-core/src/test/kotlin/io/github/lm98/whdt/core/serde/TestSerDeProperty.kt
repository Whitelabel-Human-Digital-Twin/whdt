package io.github.lm98.whdt.core.serde

import io.github.lm98.whdt.core.hdt.model.Model
import io.github.lm98.whdt.core.hdt.model.property.GenericProperty
import io.github.lm98.whdt.core.hdt.model.property.Property
import io.github.lm98.whdt.core.hdt.model.property.PropertyValue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class TestSerDeProperty: FunSpec({
    test("Test SerDe GenericProperty") {
        val serde = Stub.propertyJsonSerDe()

        val prop: Property = GenericProperty("username", "username", value = PropertyValue.StringPropertyValue("leona"))
        val serialized = serde.serialize(prop)
        //println(serialized)
        val deserialized = serde.deserialize(serialized)

        deserialized shouldBe prop
    }

    test("Test SerDe Model") {
        val serde = Stub.modelJsonSerDe()
        val model = Model(listOf(
            GenericProperty("username", "username", value = PropertyValue.StringPropertyValue("leona")),
            GenericProperty("password", "password", value = PropertyValue.StringPropertyValue("123456")),
        ))
        val serialized = serde.serialize(model)
        //println(serialized)
        val deserialized = serde.deserialize(serialized)

        deserialized shouldBe model
    }
})