package io.github.whdt.core.serde.modules

import io.github.whdt.core.hdt.model.property.*
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

val propertyModule = SerializersModule {
    polymorphic(PropertyValue::class) {
        subclass(PropertyValue.EmptyPropertyValue::class, PropertyValue.EmptyPropertyValue.serializer())
        subclass(PropertyValue.StringPropertyValue::class, PropertyValue.StringPropertyValue.serializer())
        subclass(PropertyValue.IntPropertyValue::class, PropertyValue.IntPropertyValue.serializer())
        subclass(PropertyValue.FloatPropertyValue::class, PropertyValue.FloatPropertyValue.serializer())
        subclass(PropertyValue.BooleanPropertyValue::class, PropertyValue.BooleanPropertyValue.serializer())
    }
}