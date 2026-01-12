package io.github.whdt

import io.github.whdt.wldt.plugin.execution.WldtApp
import io.github.whdt.core.hdt.HumanDigitalTwin
import io.github.whdt.core.hdt.interfaces.digital.HttpDigitalInterface
import io.github.whdt.core.hdt.interfaces.digital.MqttDigitalInterface
import io.github.whdt.core.hdt.interfaces.physical.MqttPhysicalInterface
import io.github.whdt.core.hdt.model.Model
import io.github.whdt.core.hdt.model.property.Properties.bloodPressure
import io.github.whdt.core.hdt.model.property.Properties.firstName
import io.github.whdt.core.hdt.model.property.Properties.heartRate
import io.github.whdt.core.hdt.model.property.Properties.surname

fun main() {
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

    val httpDI = HttpDigitalInterface(
        id = "hd1"
    )

    val hdts = listOf(
        HumanDigitalTwin(
            hdtId = "hd1",
            models = listOf(model),
            physicalInterfaces = listOf(pI),
            digitalInterfaces = listOf(dI, httpDI),
        )
    )

    val startedDts = WldtApp().addStartAll(hdts)
    println("Started Dts: ${startedDts.map { it.getOrNull() }}")
}