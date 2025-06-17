import execution.ExecutionStrategy
import hdt.HumanDigitalTwin
import it.wldt.adapter.mqtt.digital.MqttDigitalAdapter
import it.wldt.adapter.mqtt.digital.MqttDigitalAdapterConfiguration
import it.wldt.adapter.mqtt.digital.topic.MqttQosLevel
import it.wldt.adapter.mqtt.physical.MqttPhysicalAdapter
import it.wldt.adapter.mqtt.physical.MqttPhysicalAdapterConfiguration
import it.wldt.core.engine.DigitalTwin
import it.wldt.core.engine.DigitalTwinEngine
import shadowing.DefaultShadowingFunction
import kotlin.collections.forEach

/**
 * WLDTMqttExecutionStrategy is an implementation of ExecutionStrategy that sets up a WLDT Digital Twin Engine
 * with MQTT adapters for the provided HumanDigitalTwins.
 */
object WLDTMqttExecutionStrategy: ExecutionStrategy<Unit> {
    private val dtEngine = DigitalTwinEngine()
    private val MQTT_BROKER = System.getenv("MQTT_BROKER") ?: "test.mosquitto.org"
    private val MQTT_BROKER_PORT = System.getenv("MQTT_BROKER_PORT")?.toInt() ?: 1883

    override fun execute(dts: List<HumanDigitalTwin>): Result<Unit> {
        setupDigitalTwins(dts)
        dtEngine.startAll()
        return Result.success(Unit)
    }

    fun setupDigitalTwins(dts: List<HumanDigitalTwin>) {
        dts.forEach { it ->
            val id = it.id
            val dt = factory.HumanDigitalTwinFactories.fromHumanDigitalTwin(it)

            val mqttPhysicalConfigBuilder = MqttPhysicalAdapterConfiguration.builder(MQTT_BROKER, MQTT_BROKER_PORT)
            //SETUP PROPERTIES
            it.models.flatMap { it.properties }.forEach {
                mqttPhysicalConfigBuilder.addPhysicalAssetPropertyAndTopic(
                    it.internalName,
                    it,
                    "${id}/sensor/${it.internalName}",
                    it::deserialize
                )
            }
            val mqttPhysicalConfig = mqttPhysicalConfigBuilder.build()
            val mqttPhysicalAdapter = MqttPhysicalAdapter("${it.id}-mqtt-pa",mqttPhysicalConfig)
            dt.addPhysicalAdapter(mqttPhysicalAdapter)

            val mqttDigitalConfigBuilder = MqttDigitalAdapterConfiguration.builder(MQTT_BROKER, MQTT_BROKER_PORT)
            it.models.flatMap { it.properties }.forEach {
                mqttDigitalConfigBuilder.addPropertyTopic(
                    it.internalName,
                    "${id}/state/${it.internalName}",
                    MqttQosLevel.MQTT_QOS_0,
                    it::serialize
                )
            }
            val mqttDigitalConfig = mqttDigitalConfigBuilder.build()
            val mqttDigitalAdapter = MqttDigitalAdapter("${it.id}-mqtt-da", mqttDigitalConfig)
            dt.addDigitalAdapter(mqttDigitalAdapter)

            dtEngine.addDigitalTwin(dt)
        }
    }
}