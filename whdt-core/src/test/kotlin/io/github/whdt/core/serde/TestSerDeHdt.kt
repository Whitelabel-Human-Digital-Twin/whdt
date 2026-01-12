package io.github.whdt.core.serde

import io.github.whdt.core.hdt.HumanDigitalTwin
import io.github.whdt.core.hdt.interfaces.digital.MqttDigitalInterface
import io.github.whdt.core.hdt.interfaces.physical.MqttPhysicalInterface
import io.github.whdt.core.hdt.model.Model
import io.github.whdt.core.hdt.model.id.HdtId
import io.github.whdt.core.hdt.model.property.Properties.bloodPressure
import io.github.whdt.core.hdt.model.property.Properties.firstName
import io.github.whdt.core.hdt.model.property.Properties.heartRate
import io.github.whdt.core.hdt.model.property.Properties.surname
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class TestSerDeHdt: FunSpec({
  test("Test SerDe HumanDigitalTwin") {
      val properties = listOf(
          firstName("John"),
          surname("Doe"),
          bloodPressure(
              systolic = 120,
              diastolic = 80,
          ),
          heartRate(72)
      )
      val model = Model(properties)
      val pI = MqttPhysicalInterface(
          clientId = "hd1",
      )
      val dI = MqttDigitalInterface(
          clientId = "hd1",
      )
      val hdt = HumanDigitalTwin(
          id = HdtId.of("hd1"),
          models = listOf(model),
          physicalInterfaces = listOf(pI),
          digitalInterfaces = listOf(dI),
      )

      val serialized = Stub.hdtJsonSerDe().serialize(hdt)
      println(serialized)
      val deserialized = Stub.hdtJsonSerDe().deserialize(serialized)

      deserialized shouldBe hdt
  }
})