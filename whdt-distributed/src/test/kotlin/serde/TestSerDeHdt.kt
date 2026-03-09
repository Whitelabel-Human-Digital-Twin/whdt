package serde

import io.github.whdt.core.hdt.HdtId
import io.github.whdt.core.hdt.HumanDigitalTwin
import io.github.whdt.core.hdt.interfaces.digital.DigitalInterfaceName
import io.github.whdt.core.hdt.interfaces.digital.MqttDigitalInterface
import io.github.whdt.core.hdt.interfaces.physical.MqttPhysicalInterface
import io.github.whdt.core.hdt.interfaces.physical.PhysicalInterfaceName
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

class TestSerDeHdt: FunSpec({
  test("Test SerDe HumanDigitalTwin") {
      val hdtId = HdtId("hdt-1")
      val modelName = ModelName("my-model")
      val modelId = ModelId("$hdtId:$modelName")
      val properties = listOf(
          testProperty(modelId, PropertyName("First Name"), "John".pv()),
          testProperty(modelId, PropertyName("Surname"), "Doe".pv())
      )
      val model = Model(hdtId, modelName, ModelDescription("Test Model"), properties)
      val pI = MqttPhysicalInterface(
          hdtId = hdtId,
          name = PhysicalInterfaceName("mqtt-phys-int")
      )
      val dI = MqttDigitalInterface(
          hdtId = hdtId,
          name = DigitalInterfaceName("mqtt-digital-int")
      )
      val hdt = HumanDigitalTwin(
          hdtId = hdtId,
          models = listOf(model),
          physicalInterfaces = listOf(pI),
          digitalInterfaces = listOf(dI),
      )

      val serialized = Stub.hdtJsonSerDe().serialize(hdt)
      println(serialized)
      val deserialized = Stub.hdtJsonSerDe().deserialize(serialized)

      deserialized shouldBe hdt
  }
}) {
    companion object {
        fun testProperty(modelId: ModelId, name: PropertyName, value: PropertyValue): Property {
            return Property(
                modelId = modelId,
                name = name,
                description = PropertyDescription(""),
                timestamp = Clock.System.now(),
                value = value
            )
        }
    }
}