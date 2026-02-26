package io.github.whdt.dsl.builder

import io.github.whdt.dsl.WhdtDsl
import io.github.whdt.core.hdt.model.property.PropertyValue
import io.github.whdt.core.hdt.model.Model
import io.github.whdt.core.hdt.model.property.Property
import kotlin.time.Clock
import kotlin.time.Instant

@WhdtDsl
class ModelBuilder(private val modelId: String) {
    private var description: String? = null
    private val properties = mutableListOf<Property>()

    fun description(text: String) {
        description = text
    }

    /**
     * Creates a "pending" property spec, then you call `.of(...)` to set the value and add it.
     */
    fun property(id: String, name: String, description: String, timestamp: Instant = Clock.System.now()): PropertySpec =
        PropertySpec(
            add = { value ->
                properties += Property(
                    id = id,
                    name = name,
                    description = description,
                    timestamp = timestamp,
                    value = value
                )
            }
        )

    fun build(): Model =
        Model(
            id = modelId,
            description = description ?: "Model description for $modelId",
            properties = properties.toList()
        )

    class PropertySpec(private val add: (PropertyValue) -> Unit) {
        fun of(value: PropertyValue) {
            add(value)
        }

        // Convenience overloads (so `.of(123)` works)
        fun of(value: String) = of(PropertyValue.StringPropertyValue(value))
        fun of(value: Int) = of(PropertyValue.IntPropertyValue(value))
        fun of(value: Float) = of(PropertyValue.FloatPropertyValue(value))
        fun of(value: Boolean) = of(PropertyValue.BooleanPropertyValue(value))
        fun of(value: Double) = of(PropertyValue.DoublePropertyValue(value))
        fun of(value: Long) = of(PropertyValue.LongPropertyValue(value))

        fun empty() = of(PropertyValue.EmptyPropertyValue)
    }
}