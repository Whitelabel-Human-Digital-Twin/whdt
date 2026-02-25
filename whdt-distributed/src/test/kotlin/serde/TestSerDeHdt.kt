package serde

import io.github.whdt.core.hdt.HumanDigitalTwin
import io.github.whdt.core.hdt.interfaces.digital.MqttDigitalInterface
import io.github.whdt.core.hdt.interfaces.physical.MqttPhysicalInterface
import io.github.whdt.core.hdt.model.Model
import io.github.whdt.core.hdt.model.id.HdtId
import io.github.whdt.core.hdt.model.property.Property
import io.github.whdt.core.hdt.model.property.PropertyValue
import io.github.whdt.distributed.serde.Stub
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Clock

class TestSerDeHdt: FunSpec({
  test("Test SerDe HumanDigitalTwin") {
      val hdtId = HdtId.of("hd-1")
      val properties = listOf(
          testProperty("First Name", PropertyValue.StringPropertyValue("John")),
          testProperty("Surname", PropertyValue.StringPropertyValue("Doe"))
      )
      val model = Model("my-model", "Test Model", properties)
      val pI = MqttPhysicalInterface(
          hdtId = hdtId,
      )
      val dI = MqttDigitalInterface(
          hdtId = hdtId,
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
        fun testProperty(name: String, value: PropertyValue): Property {
            return Property(
                id = name,
                name = name,
                description = "",
                timestamp = Clock.System.now(),
                value = value
            )
        }
    }
}