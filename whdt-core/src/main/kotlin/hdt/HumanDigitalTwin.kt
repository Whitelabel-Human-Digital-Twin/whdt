package hdt

interface Model {
    val properties: List<Property>
}

interface HumanDigitalTwin {
    val models: List<Model>
}