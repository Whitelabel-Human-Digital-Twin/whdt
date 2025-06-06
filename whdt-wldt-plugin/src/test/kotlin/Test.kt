import execution.App
import hdt.model.BloodPressure
import hdt.model.GivenName
import hdt.model.HeartRate
import hdt.model.Surname

fun main() {
    val model = hdt.ModelImpl(
        properties = listOf(
            GivenName("John"),
            Surname("Doe"),
            BloodPressure(
                systolic = 120,
                diastolic = 80,
            ),
            HeartRate(72)
        ),)

    val hdts = listOf(
        hdt.HumanDigitalTwinImpl(
            id = "hd1",
            models = listOf(model)
        )
    )

    App(hdts).run(WLDTMqttExecutionStrategy)
}