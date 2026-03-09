package serde

import io.github.whdt.core.hdt.model.Model
import io.github.whdt.core.hdt.model.id.HdtId
import io.github.whdt.core.hdt.model.property.Property
import io.github.whdt.core.hdt.model.property.PropertyValue
import io.github.whdt.core.hdt.model.property.PropertyValue.Companion.pv
import io.github.whdt.distributed.serde.Stub
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Clock

class TestSerDeProperty: FunSpec({
    test("Test SerDe GenericProperty") {
        val serde = Stub.propertyJsonSerDe()
        val modelId = "my-model"
        val prop = Property(
            modelId,
            name = "username",
            description = "The username of the user.",
            timestamp = Clock.System.now(),
            value = PropertyValue.StringPropertyValue("leona"),
        )
        val serialized = serde.serialize(prop)
        //println(serialized)
        val deserialized = serde.deserialize(serialized)

        deserialized shouldBe prop
    }

    test("Test SerDe Model") {
        val serde = Stub.modelJsonSerDe()
        val now = Clock.System.now()
        val model = Model(
            HdtId.of("dt-1"),
            "my-model",
            "Test Model",
            listOf(
                Property("username", "username", "", now, "leona".pv()),
                Property("password", "password", "", now, "123456".pv()),
                )
        )
        val serialized = serde.serialize(model)
        //println(serialized)
        val deserialized = serde.deserialize(serialized)

        deserialized shouldBe model
    }
})