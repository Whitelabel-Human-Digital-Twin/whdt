package io.github.whdt.wldt.plugin.augmentation.ml.event

import io.github.whdt.augmentation.event.AugmentationEvent

object ModelCallEvents {
    class ModelCallRequest: AugmentationEvent<Nothing>(TYPE) {
        companion object {
            const val TYPE = "model.call.request"
            fun buildEventType(): String =
                buildEventType(EVENT_BASIC_TYPE, TYPE)
        }
    }

    class ModelCallResult(
        val body: String,
    ): AugmentationEvent<String>(TYPE, body) {
        companion object {
            const val TYPE = "model.call.result"
            fun buildEventType(): String =
                buildEventType(EVENT_BASIC_TYPE, TYPE)
        }
    }
}