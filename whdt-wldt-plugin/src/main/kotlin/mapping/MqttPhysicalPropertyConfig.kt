package mapping

import hdt.Property

data class MqttPhysicalPropertyConfig<T: Property>(
    val name: String,
    val topic: String,
    val initialValue: T,
    val topicFunction: (String) -> T
)