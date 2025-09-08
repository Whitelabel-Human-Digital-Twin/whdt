package io.github.whdt.core.serde.modules

import kotlinx.serialization.modules.SerializersModule

val hdtModule = SerializersModule {
    include(propertyModule)
    include(interfaceModule)
}