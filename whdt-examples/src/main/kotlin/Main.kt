package io.github.whdt

import io.github.whdt.core.hdt.HumanDigitalTwin
import io.github.whdt.core.hdt.interfaces.digital.HttpDigitalInterface
import io.github.whdt.core.hdt.interfaces.digital.MqttDigitalInterface
import io.github.whdt.core.hdt.interfaces.physical.MqttPhysicalInterface
import io.github.whdt.core.hdt.model.Model
import io.github.whdt.core.hdt.model.id.HdtId
import io.github.whdt.core.hdt.model.property.Property
import io.github.whdt.core.hdt.model.property.PropertyValue
import io.github.whdt.wldt.plugin.execution.WldtApp
import kotlin.time.Clock

fun main() {
    val hdtId = HdtId.of("Mimosa_1")
    val modelId = "my-model"
    val properties = listOf(
        testProperty(modelId, "First Name", PropertyValue.StringPropertyValue("John")),
        testProperty(modelId, "Surname", PropertyValue.StringPropertyValue("Doe"))
    )
    val model = Model(hdtId, "my-model", "Test Model", properties)

    val pI = MqttPhysicalInterface(
        hdtId = hdtId,
    )

    val dI = MqttDigitalInterface(
        hdtId = hdtId,
    )

    val httpDI = HttpDigitalInterface(
        hdtId = hdtId,
    )

    val hdts = listOf(
        HumanDigitalTwin(
            hdtId = hdtId,
            models = listOf(model),
            physicalInterfaces = listOf(pI),
            digitalInterfaces = listOf(dI, httpDI),
        )
    )

    val startedDts = WldtApp().addStartAll(hdts)
    println("Started Dts: ${startedDts.map { it.getOrNull() }}")
}

fun testProperty(modelId: String, name: String, value: PropertyValue): Property {
    return Property(
        modelId = modelId,
        name = name,
        description = "",
        timestamp = Clock.System.now(),
        value = value
    )
}