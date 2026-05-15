package io.github.whdt.wldt.plugin.augmentation.ml

import io.github.whdt.augmentation.AugmentationFunction
import io.github.whdt.augmentation.event.AugmentationEvent
import io.github.whdt.wldt.plugin.augmentation.ml.event.ModelCallEvents.ModelCallRequest
import io.github.whdt.wldt.plugin.augmentation.ml.event.ModelCallEvents.ModelCallResult
import it.wldt.core.event.WldtEventFilter
import org.jetbrains.kotlinx.dl.onnx.inference.OnnxInferenceModel
import java.util.*

/**
 * An [AugmentationFunction] wrapping an [OnnxInferenceModel], living in [pathToModel].
 * Updates to the model should overwrite the old model in the same path.
 */
class ModelCallAugmentationFunction(
    private val _id: String,
    val pathToModel: String,
): AugmentationFunction {

    private var model: OnnxInferenceModel
    val inputEvents = WldtEventFilter()
    val outputEvents = WldtEventFilter()

    init {
        require(pathToModel.isNotBlank() && pathToModel.endsWith(".onnx")) {
            "pathToModel cannot be blank or end with something different than .onnx"
        }
        model = OnnxInferenceModel(pathToModel)
        inputEvents.add(ModelCallRequest.buildEventType())
        outputEvents.add(ModelCallResult.buildEventType())
    }

    override fun getId(): String = _id
    override fun inputEvents(): WldtEventFilter = inputEvents
    override fun outputEvents(): WldtEventFilter = outputEvents

    override fun receive(input: AugmentationEvent<*>): Optional<AugmentationEvent<*>> {
        return if (input is ModelCallRequest) {
            model = getUpdatedModel(pathToModel)
            Optional.of(ModelCallResult("todo"))
        } else {
            Optional.empty()
        }
    }

    /**
     * Returns the updated [OnnxInferenceModel], living in [pathToUpdatedModel]
     *
     * @param pathToUpdatedModel the path of the updated model.
     * @return the updated [OnnxInferenceModel].
     */
    fun getUpdatedModel(pathToUpdatedModel: String): OnnxInferenceModel {
        return OnnxInferenceModel(pathToUpdatedModel)
    }
}