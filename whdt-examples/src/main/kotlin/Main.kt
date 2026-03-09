package io.github.whdt

import io.github.whdt.core.hdt.HdtId
import io.github.whdt.core.hdt.HumanDigitalTwin
import io.github.whdt.core.hdt.interfaces.digital.DigitalInterfaceName
import io.github.whdt.core.hdt.interfaces.digital.HttpDigitalInterface
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
import io.github.whdt.wldt.plugin.execution.WldtApp
import kotlin.time.Clock

fun main() {
    val hdtId = HdtId("Mimosa_1")
    val modelId = ModelId("$hdtId:my-model")
    val properties = listOf(
        testProperty(modelId, "First Name", PropertyValue.StringPropertyValue("John")),
        testProperty(modelId, "Surname", PropertyValue.StringPropertyValue("Doe"))
    )
    val model = Model(hdtId, ModelName("my-model"), ModelDescription("Test Model"), properties)

    val pI = MqttPhysicalInterface(
        hdtId = hdtId,
        name = PhysicalInterfaceName("mqtt-physical-int"),
    )

    val dI = MqttDigitalInterface(
        hdtId = hdtId,
        name = DigitalInterfaceName("mqtt-digital-int"),
    )

    val httpDI = HttpDigitalInterface(
        hdtId = hdtId,
        name = DigitalInterfaceName("http-digital-int"),
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

fun testProperty(modelId: ModelId, name: String, value: PropertyValue): Property {
    return Property(
        modelId = modelId,
        name = PropertyName(name),
        description = PropertyDescription(""),
        timestamp = Clock.System.now(),
        value = value
    )
}