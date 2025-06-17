package hdt.model.property

import hdt.util.*

interface Property: Default, Deserialize, Serialize {
    val name: String
    val internalName: String
    val description: String
    val id: String
}

interface InstantProperty: Property, Instant

interface TimeRangedProperty: Property, TimePeriod