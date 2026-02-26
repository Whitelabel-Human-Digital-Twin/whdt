package io.github.whdt.dsl

import io.github.whdt.core.hdt.interfaces.digital.DigitalInterfaceImpl
import io.github.whdt.core.hdt.interfaces.digital.DigitalInterfaceType
import io.github.whdt.core.hdt.interfaces.physical.PhysicalInterfaceImpl
import io.github.whdt.core.hdt.model.id.HdtId
import io.github.whdt.core.hdt.model.property.PropertyValue
import io.github.whdt.core.hdt.storage.StorageType
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.maps.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class TestWhdtDsl: FunSpec({
    test("DSL builds models and applies default model description") {

        val twin = hdt("my-hdt") {
            model("my-model-1") {
                description("Model 1 description")
                property("property-1", "name-1", "description-1").of(42)
            }

            model("my-model-2") {
                // no explicit description
                property("property-2", "name-2", "description-2").of("hello")
                property("property-3", "name-3", "description-3").of(true)
            }
        }

        twin.hdtId shouldBe HdtId("my-hdt")
        twin.models shouldHaveSize 2

        val m1 = twin.models[0]
        m1.id shouldBe "my-model-1"
        m1.description shouldBe "Model 1 description"
        m1.properties shouldHaveSize 1

        val p1 = m1.properties.first()
        val v1 = p1.value.shouldBeInstanceOf<PropertyValue.IntPropertyValue>()
        v1.value shouldBe 42

        val m2 = twin.models[1]
        m2.id shouldBe "my-model-2"
        m2.description.contains("my-model-2") shouldBe true
        m2.properties shouldHaveSize 2

        val v2 = m2.properties[0].value.shouldBeInstanceOf<PropertyValue.StringPropertyValue>()
        v2.value shouldBe "hello"

        val v3 = m2.properties[1].value.shouldBeInstanceOf<PropertyValue.BooleanPropertyValue>()
        v3.value shouldBe true
    }

    test("DSL builds digital, physical interfaces and storage with config") {

        val twin = hdt("my-hdt") {

            digitalInterface("my-digital-interface") {
                type(DigitalInterfaceType.HTTP)
                config {
                    "baseUrl" to "https://example.test"
                    "timeoutMs" to "1000"
                }
            }

            physicalInterface("my-physical-interface") {
                config {
                    "broker" to "localhost"
                }
            }

            storage("my-storage", StorageType.DB_MONGO) {
                config {
                    "uri" to "mongodb://localhost:27017"
                    "db" to "hdt"
                }
            }
        }

        twin.digitalInterfaces shouldHaveSize 1
        val di = twin.digitalInterfaces.single()
            .shouldBeInstanceOf<DigitalInterfaceImpl>()

        di.id shouldBe "my-digital-interface"
        di.interfaceType shouldBe DigitalInterfaceType.HTTP
        di.hdtId shouldBe HdtId("my-hdt")
        di.config shouldContainExactly mapOf(
            "baseUrl" to "https://example.test",
            "timeoutMs" to "1000"
        )

        twin.physicalInterfaces shouldHaveSize 1
        val pi = twin.physicalInterfaces.single()
            .shouldBeInstanceOf<PhysicalInterfaceImpl>()

        pi.id shouldBe "my-physical-interface"
        pi.hdtId shouldBe HdtId("my-hdt")
        pi.config shouldContainExactly mapOf(
            "broker" to "localhost"
        )

        twin.storages shouldHaveSize 1
        val storage = twin.storages.single()

        storage.id shouldBe "my-storage"
        storage.storageType shouldBe StorageType.DB_MONGO
        storage.config shouldContainExactly mapOf(
            "uri" to "mongodb://localhost:27017",
            "db" to "hdt"
        )
    }
})