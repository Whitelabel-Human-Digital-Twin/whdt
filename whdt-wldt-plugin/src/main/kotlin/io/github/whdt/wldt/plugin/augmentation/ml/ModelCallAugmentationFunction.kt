package io.github.whdt.wldt.plugin.augmentation.ml

import io.github.whdt.augmentation.AugmentationFunction
import io.github.whdt.augmentation.event.AugmentationEvent
import io.github.whdt.wldt.plugin.augmentation.ml.event.ModelCallEvents.ModelCallRequest
import io.github.whdt.wldt.plugin.augmentation.ml.event.ModelCallEvents.ModelCallResult
import it.wldt.core.event.WldtEventFilter
import java.util.*

class ModelCallAugmentationFunction(
    val _id: String,
): AugmentationFunction {
    val inputEvents = WldtEventFilter()
    val outputEvents = WldtEventFilter()

    init {
        inputEvents.add(ModelCallRequest.buildEventType())
        outputEvents.add(ModelCallResult.buildEventType())
    }

    override fun getId(): String = _id
    override fun inputEvents(): WldtEventFilter = inputEvents
    override fun outputEvents(): WldtEventFilter = outputEvents

    override fun receive(input: AugmentationEvent<*>): Optional<AugmentationEvent<*>> {
        return if (input is ModelCallRequest) {
            Optional.of(ModelCallResult("todo"))
        } else {
            Optional.empty()
        }
    }
}