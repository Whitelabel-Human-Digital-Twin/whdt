package io.github.lm98.whdt.core.serde

import io.github.lm98.whdt.core.hdt.model.Model
import io.github.lm98.whdt.core.hdt.model.property.GenericProperty
import io.github.lm98.whdt.core.hdt.model.property.Property
import io.github.lm98.whdt.core.hdt.model.property.PropertyValue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.json.Json

class TestSerDeProperty: FunSpec({
    test("Test SerDe GenericProperty") {
        val json = Json {
            serializersModule = io.github.lm98.whdt.core.serde.modules.propertyModule
            classDiscriminator = "type"
            prettyPrint = true
        }

        val prop: Property = GenericProperty("username", "username", value = PropertyValue.StringPropertyValue("leona"))
        val serialized = json.encodeToString(prop)
        //println(serialized)
        val deserialized = json.decodeFromString<Property>(serialized)

        deserialized shouldBe prop
    }

    test("Test SerDe Model") {
        val json = Json {
            serializersModule = io.github.lm98.whdt.core.serde.modules.propertyModule
            classDiscriminator = "type"
            prettyPrint = true
        }

        val model = Model(listOf(
            GenericProperty("username", "username", value = PropertyValue.StringPropertyValue("leona")),
            GenericProperty("password", "password", value = PropertyValue.StringPropertyValue("123456")),
        ))
        val serialized = json.encodeToString(Model.serializer(), model)
        //println(serialized)
        val deserialized = json.decodeFromString(Model.serializer(),serialized)

        deserialized shouldBe model
    }
})