import it.wldt.adapter.digital.DigitalAdapter
import it.wldt.adapter.physical.PhysicalAdapter
import it.wldt.core.engine.DigitalTwin
import it.wldt.core.model.ShadowingFunction

class DigitalTwinConfiguration<C>(
    val id: String,
    val shad: ShadowingFunction,
    val physicalAdapters: List<PhysicalAdapter>,
    val digitalAdapters: List<DigitalAdapter<C>>,
) {
    companion object {
        fun <C> builder(): DigitalTwinConfigurationBuilder<C> {
            return DigitalTwinConfigurationBuilder()
        }
    }

    fun toDigitalTwin(): DigitalTwin {
        return DigitalTwin(id, shad).apply {
            physicalAdapters.forEach { addPhysicalAdapter(it) }
            digitalAdapters.forEach { addDigitalAdapter(it) }
        }
    }
}

class DigitalTwinConfigurationBuilder<C> {
    private lateinit var id: String
    private lateinit var shad: ShadowingFunction
    private val physicalAdapters = mutableListOf<PhysicalAdapter>()
    private val digitalAdapters = mutableListOf<DigitalAdapter<C>>()

    fun id(id: String) = apply { this.id = id }
    fun shad(shad: ShadowingFunction) = apply { this.shad = shad }
    fun addPhysicalAdapter(adapter: PhysicalAdapter) = apply { physicalAdapters.add(adapter) }
    fun addDigitalAdapter(adapter: DigitalAdapter<C>) = apply { digitalAdapters.add(adapter) }

    fun build(): DigitalTwinConfiguration<C> {
        return DigitalTwinConfiguration(id, shad, physicalAdapters, digitalAdapters)
    }
}