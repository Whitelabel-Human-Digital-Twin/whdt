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

    polymorphic(Argument::class) {
        subclass(Argument.EmptyArgument::class, Argument.EmptyArgument.serializer())
        subclass(Argument.StringArgument::class, Argument.StringArgument.serializer())
        subclass(Argument.IntArgument::class, Argument.IntArgument.serializer())
        subclass(Argument.FloatArgument::class, Argument.FloatArgument.serializer())
        subclass(Argument.BooleanArgument::class, Argument.BooleanArgument.serializer())
    }
}