package io.github.lm98.whdt.core.serde

import io.github.lm98.whdt.core.hdt.model.Model
import io.github.lm98.whdt.core.hdt.model.property.BloodPressure
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

    test("Serialized property should contain all fields") {
        val serde = Stub.propertyJsonSerDe()

        val time = System.currentTimeMillis()
        val prop: Property = BloodPressure(120, 80, timestamp = time)
        val serialized = serde.serialize(prop)
        //println(serialized)
        // serialized should contain internal name, name and other fields
        serialized shouldBe """
            {
                "type": "blood-pressure",
                "systolic": 120,
                "diastolic": 80,
                "name": "Blood Pressure",
                "internalName": "blood-pressure",
                "description": "The pressure of blood in the circulatory system.",
                "id": "loinc:8480-6",
                "timestamp": $time
            }
        """.trimIndent()
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