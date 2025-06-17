import execution.App
import hdt.model.ModelImpl
import hdt.model.property.BloodPressure
import hdt.model.property.GivenName
import hdt.model.property.HeartRate
import hdt.model.property.Surname

fun main() {
    val properties = listOf(
        GivenName("John"),
        Surname("Doe"),
        BloodPressure(
            systolic = 120,
            diastolic = 80,
        ),
        HeartRate(72)
    )

    val model = ModelImpl(properties)

    val pI = hdt.interfaces.physical.MqttPhysicalInterface(
        properties = properties,
        clientId = "hd1",
    )

    val dI = hdt.interfaces.digital.MqttDigitalInterface(
        properties = properties,
        clientId = "hd1",
    )

    val hdts = listOf(
        hdt.HumanDigitalTwinImpl(
            id = "hd1",
            models = listOf(model),
            physicalInterfaces = listOf(pI),
            digitalInterfaces = listOf(dI),
        )
    )

    App(hdts).run(WLDTExecutionStrategy)
}