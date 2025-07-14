package io.github.lm98.whdt.core.serde

import io.github.lm98.whdt.core.hdt.HumanDigitalTwin
import io.github.lm98.whdt.core.hdt.interfaces.digital.DigitalInterface
import io.github.lm98.whdt.core.hdt.interfaces.physical.PhysicalInterface
import io.github.lm98.whdt.core.hdt.model.Model
import io.github.lm98.whdt.core.hdt.model.property.Property
import io.github.lm98.whdt.core.serde.modules.hdtModule
import io.github.lm98.whdt.core.serde.modules.interfaceModule
import io.github.lm98.whdt.core.serde.modules.propertyModule
import kotlinx.serialization.json.Json

object Stub {
    private val hdtJson = Json {
        serializersModule = hdtModule
        classDiscriminator = "type"
        prettyPrint = true
    }

    private val propertyJson = Json {
        serializersModule = propertyModule
        classDiscriminator = "type"
        prettyPrint = true
    }

    private val interfaceJson = Json {
        serializersModule = interfaceModule
        classDiscriminator = "type"
        prettyPrint = true
    }
    
    fun hdtJsonSerDe(): SerDe<HumanDigitalTwin> = jsonSerDe<HumanDigitalTwin>(hdtJson)
    fun propertyJsonSerDe(): SerDe<Property> = jsonSerDe<Property>(propertyJson)
    fun modelJsonSerDe(): SerDe<Model> = jsonSerDe<Model>(propertyJson)
    fun physicalInterfaceJsonSerDe(): SerDe<PhysicalInterface> = jsonSerDe<PhysicalInterface>(interfaceJson)
    fun digitalInterfaceJsonSerDe(): SerDe<DigitalInterface> = jsonSerDe<DigitalInterface>(interfaceJson)
}