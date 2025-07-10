package io.github.lm98.whdt.core.serde.modules

import io.github.lm98.whdt.core.hdt.interfaces.digital.DigitalInterface
import io.github.lm98.whdt.core.hdt.interfaces.digital.HttpDigitalInterface
import io.github.lm98.whdt.core.hdt.interfaces.digital.MqttDigitalInterface
import io.github.lm98.whdt.core.hdt.interfaces.physical.MqttPhysicalInterface
import io.github.lm98.whdt.core.hdt.interfaces.physical.PhysicalInterface
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

val interfaceModule = SerializersModule {
    polymorphic(PhysicalInterface::class) {
        subclass(MqttPhysicalInterface::class, MqttPhysicalInterface.serializer())
    }

    polymorphic(DigitalInterface::class) {
        subclass(HttpDigitalInterface::class, HttpDigitalInterface.serializer())
        subclass(MqttDigitalInterface::class, MqttDigitalInterface.serializer())
    }
}