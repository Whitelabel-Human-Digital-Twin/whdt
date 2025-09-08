package io.github.whdt.core.hdt.model.property

import io.github.whdt.core.hdt.model.property.PropertyValue.*

/**
 * A collection of predefined properties that can be used in various applications.
 */
object Properties {

    fun singleValueProperty(
        name: String,
        id: String,
        description: String = name,
        valueName: String = "value",
        value: PropertyValue = EmptyPropertyValue
    ): Property {
        return Property(
            name = name,
            id = id,
            description = description,
            valueMap = mapOf(valueName to value)
        )
    }

    fun firstName(
        firstName: String,
    ): Property {
        return singleValueProperty(
            "First Name",
            "first-name",
            "The first name of a person, typically used to identify them in a personal context.",
            value = StringPropertyValue(firstName))
    }

    fun surname(
        surname: String,
    ): Property {
        return singleValueProperty(
            "Surname",
            "surname",
            "The family name or last name of a person, used to identify them in a familial context.",
            value = StringPropertyValue(surname))
    }

    fun bloodPressure(
        systolic: Int = 120,
        diastolic: Int = 80,
        timestamp: Long = System.currentTimeMillis()
    ): Property {
        return Property(
            name = "Blood Pressure",
            id = "blood-pressure",
            description = "The pressure of blood in the circulatory system.",
            valueMap = mapOf(
                "systolic" to IntPropertyValue(systolic),
                "diastolic" to IntPropertyValue(diastolic),
                "timestamp" to LongPropertyValue(timestamp)
            )
        )
    }

    fun steps(count: Int = 0): Property {
        return singleValueProperty(
            name = "Steps",
            id = "steps",
            description = "The number of steps taken, typically used in fitness tracking.",
            valueName = "count",
            value = IntPropertyValue(count)
        )
    }

    fun oxygenSaturation(
        value: Float = 95.0f,
        timestamp: Long = System.currentTimeMillis()
    ): Property {
        return Property(
            name = "Oxygen Saturation",
            id = "oxygen-saturation",
            description = "The percentage of hemoglobin in the blood that is saturated with oxygen.",
            valueMap = mapOf(
                "value" to FloatPropertyValue(value),
                "timestamp" to LongPropertyValue(timestamp)
            )
        )
    }

    fun heartRate(
        bpm: Int = 72,
        timestamp: Long = System.currentTimeMillis()
    ): Property {
        return Property(
            name = "Heart Rate",
            id = "heart-rate",
            description = "The number of heartbeats per minute, typically measured in beats per minute (bpm).",
            valueMap = mapOf(
                "bpm" to IntPropertyValue(bpm),
                "timestamp" to LongPropertyValue(timestamp)
            )
        )
    }

    fun mood(
        moodScore: Int,
        timestamp: Long = System.currentTimeMillis()
    ): Property {
        return Property(
            name = "Mood",
            id = "mood",
            description = "A numerical score for the mood level.",
            valueMap = mapOf(
                "moodScore" to IntPropertyValue(moodScore),
                "timestamp" to LongPropertyValue(timestamp),
            )
        )
    }
}