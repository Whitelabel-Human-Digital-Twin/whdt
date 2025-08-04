package io.github.lm98.whdt.core.serde

import io.github.lm98.whdt.core.hdt.model.Model
import io.github.lm98.whdt.core.hdt.model.property.Properties.bloodPressure
import io.github.lm98.whdt.core.hdt.model.property.Properties.singleValueProperty
import io.github.lm98.whdt.core.hdt.model.property.Property
import io.github.lm98.whdt.core.hdt.model.property.PropertyValue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class TestSerDeProperty: FunSpec({
    test("Test SerDe GenericProperty") {
        val serde = Stub.propertyJsonSerDe()

        val prop = singleValueProperty(
            name = "Username",
            id = "username",
            description = "The username of the user.",
            value = PropertyValue.StringPropertyValue("leona"),
        )
        val serialized = serde.serialize(prop)
        //println(serialized)
        val deserialized = serde.deserialize(serialized)

        deserialized shouldBe prop
    }

    test("Serialized property should contain all fields") {
        val serde = Stub.propertyJsonSerDe()

        val time = System.currentTimeMillis()
        val prop: Property = bloodPressure(120, 80, timestamp = time)
        val serialized = serde.serialize(prop)
        //println(serialized)
        // serialized should contain internal name, name and other fields
        serialized shouldBe """
            {
                "name": "Blood Pressure",
                "id": "blood-pressure",
                "description": "The pressure of blood in the circulatory system.",
                "valueMap": {
                    "systolic": {
                        "type": "int-value",
                        "value": 120
                    },
                    "diastolic": {
                        "type": "int-value",
                        "value": 80
                    },
                    "timestamp": {
                        "type": "long-value",
                        "value": $time
                    }
                }
            }
        """.trimIndent()
    }

    test("Test SerDe Model") {
        val serde = Stub.modelJsonSerDe()
        val model = Model(listOf(
            singleValueProperty("username", "username", "", value = PropertyValue.StringPropertyValue("leona")),
            singleValueProperty("password", "password", "", value = PropertyValue.StringPropertyValue("123456")),
        ))
        val serialized = serde.serialize(model)
        //println(serialized)
        val deserialized = serde.deserialize(serialized)

        deserialized shouldBe model
    }
})