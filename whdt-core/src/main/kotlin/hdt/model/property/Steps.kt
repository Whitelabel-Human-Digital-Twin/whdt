package hdt.model.property

import com.google.gson.Gson
import hdt.util.Default
import hdt.util.Deserialize
import hdt.util.Serialize

data class Steps(
    val count: Int,
    override val name: String = "Steps",
    override val internalName: String = "steps",
    override val description: String = "The number of steps taken.",
    override val id: String = "hdt:00002-1", // custom code for step count
    override val startTime: Long = System.currentTimeMillis(),
    override val endTime: Long = System.currentTimeMillis(),
): TimeRangedProperty {
    override fun defaultValue(): Default {
        return default()
    }

    override fun deserialize(value: String): Deserialize {
        return fromJson(value)
    }

    override fun serialize(s: Serialize): String {
        return toJson(s as Steps)
    }

    companion object {
        private val gson = Gson()

        fun default(): Steps {
            return Steps(0)
        }

        fun fromJson(json: String): Steps {
            return gson.fromJson(json, Steps::class.java)
        }

        fun toJson(steps: Steps): String {
            return gson.toJson(steps)
        }
    }
}