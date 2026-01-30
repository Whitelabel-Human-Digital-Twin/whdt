package io.github.whdt.distributed.serde.modules

import kotlinx.serialization.modules.SerializersModule

val hdtModule = SerializersModule {
    include(propertyModule)
    include(interfaceModule)
}