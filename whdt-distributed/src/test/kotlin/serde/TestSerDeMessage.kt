package serde

import io.github.whdt.core.hdt.model.id.HdtId
import io.github.whdt.core.hdt.model.property.Properties.bloodPressure
import io.github.whdt.distributed.id.SenderId
import io.github.whdt.distributed.message.Message
import io.github.whdt.distributed.serde.Stub
import io.kotest.core.spec.style.FunSpec
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class TestSerDeMessage: FunSpec({
    fun Message.assertEqualsIgnoringReceivedAt(other: Message) {
        assertEquals(hdt, other.hdt)
        assertEquals(sender, other.sender)
        assertEquals(sentAt, other.sentAt)
        assertEquals(payload, other.payload)
    }


    test("Serialize Message") {
      val hdtId = HdtId("1")
      val now = Clock.System.now().toEpochMilliseconds()
      val property = bloodPressure(timestamp = now)
      val message = Message(
          hdt = hdtId,
          sender = SenderId("test-engine"),
          sentAt = now,
          receivedAt = now,
          payload = Stub.propertyJsonSerDe().serializeToJsonElement(property)
      )
      val serialized = Stub.messageJsonSerDe().serialize(message)
      //println(serialized)
      val deserialized = Stub.messageJsonSerDe().deserialize(serialized)
      message.assertEqualsIgnoringReceivedAt(deserialized)
      val deserializedProperty = Stub.propertyJsonSerDe().deserializeFromJsonElement(deserialized.payload)
      assertEquals(property, deserializedProperty)
  }
})