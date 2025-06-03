package hdt

data class BloodPressure(
    val systolic: Int,
    val diastolic: Int,
    override val name: String = "Blood Pressure",
    override val internalName: String = "blood-pressure",
    override val description: String = "The pressure of blood in the circulatory system.",
    override val id: String = "loinc:8480-6" // LOINC code for blood pressure measurement,
) : Property