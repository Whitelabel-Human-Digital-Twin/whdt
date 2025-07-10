package io.github.lm98.whdt.core.serde.modules

import io.github.lm98.whdt.core.hdt.model.property.*
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

val propertyModule = SerializersModule {
    polymorphic(Property::class) {
        subclass(BloodOxygen::class, BloodOxygen.serializer())
        subclass(BloodPressure::class, BloodPressure.serializer())
        subclass(GenericProperty::class, GenericProperty.serializer())
        subclass(GivenName::class, GivenName.serializer())
        subclass(HeartRate::class, HeartRate.serializer())
        subclass(Mood::class, Mood.serializer())
        subclass(Steps::class, Steps.serializer())
        subclass(Surname::class, Surname.serializer())
    }

    polymorphic(PropertyValue::class) {
        subclass(PropertyValue.EmptyPropertyValue::class, PropertyValue.EmptyPropertyValue.serializer())
        subclass(PropertyValue.StringPropertyValue::class, PropertyValue.StringPropertyValue.serializer())
        subclass(PropertyValue.IntPropertyValue::class, PropertyValue.IntPropertyValue.serializer())
        subclass(PropertyValue.FloatPropertyValue::class, PropertyValue.FloatPropertyValue.serializer())
        subclass(PropertyValue.BooleanPropertyValue::class, PropertyValue.BooleanPropertyValue.serializer())
    }
}