package io.github.whdt.core.hdt.interfaces.digital

import io.github.whdt.core.hdt.HdtId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("mqtt-digital-interface")
data class MqttDigitalInterface(
    override val hdtId: HdtId,
    override val name: DigitalInterfaceName,
    val broker: String = "localhost",
    val port: Int = 1883,
    override val config: Map<String, String> = emptyMap(),
) : DigitalInterface {
    override val interfaceType: DigitalInterfaceType = DigitalInterfaceType.MQTT
    override val id: DigitalInterfaceId = DigitalInterfaceId("$hdtId:$name")
}