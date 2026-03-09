package serde

import io.github.whdt.core.hdt.HdtId
import io.github.whdt.core.hdt.model.Model
import io.github.whdt.core.hdt.model.ModelDescription
import io.github.whdt.core.hdt.model.ModelId
import io.github.whdt.core.hdt.model.ModelName
import io.github.whdt.core.hdt.model.property.Property
import io.github.whdt.core.hdt.model.property.PropertyDescription
import io.github.whdt.core.hdt.model.property.PropertyName
import io.github.whdt.core.hdt.model.property.PropertyValue
import io.github.whdt.core.hdt.model.property.PropertyValue.Companion.pv
import io.github.whdt.distributed.serde.Stub
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Clock
import kotlin.time.Instant

class TestSerDeProperty: FunSpec({
    test("Test SerDe GenericProperty") {
        val serde = Stub.propertyJsonSerDe()
        val modelId = ModelId("my-model")
        val prop = Property(
            modelId,
            name = PropertyName("username"),
            description = PropertyDescription("The username of the user."),
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
        val modelId = ModelId("my-model")
        val model = Model(
            HdtId("dt-1"),
            ModelName("my-model"),
            ModelDescription("Test Model"),
            listOf(
                buildProperty(modelId, "username", now, "leona".pv()),
                buildProperty(modelId, "password", now, "123456".pv()),
                )
        )
        val serialized = serde.serialize(model)
        //println(serialized)
        val deserialized = serde.deserialize(serialized)

        deserialized shouldBe model
    }
})

fun buildProperty(modelId: ModelId, name: String, timestamp: Instant, value: PropertyValue): Property {
    return Property(
        modelId,
        PropertyName(name),
        PropertyDescription(""),
        timestamp,
        value
    )
}