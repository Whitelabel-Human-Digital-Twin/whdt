package io.github.whdt.core.hdt.interfaces.physical

import io.github.whdt.core.hdt.HdtId
import io.github.whdt.core.hdt.HdtIdFactory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("mqtt-physical-interface")
data class MqttPhysicalInterface(
    override val hdtId: HdtId,
    override val name: PhysicalInterfaceName,
    val broker: String = "localhost",
    val port: Int = 1883,
    override val config: Map<String, String> = emptyMap(),
) : PhysicalInterface {
    override val interfaceType: PhysicalInterfaceType = PhysicalInterfaceType.MQTT
    override val id = HdtIdFactory.physicalInterfaceId(hdtId, name)
}