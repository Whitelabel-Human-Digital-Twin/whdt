package io.github.whdt.wldt.plugin.augmentation.ml

import io.github.whdt.augmentation.event.AugmentationEvent
import io.github.whdt.wldt.plugin.augmentation.ml.event.ModelCallEvents.ModelCallRequest
import io.github.whdt.wldt.plugin.augmentation.ml.event.ModelCallEvents.ModelCallResult
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import java.util.*

class ModelCallAugmentationFunctionUnitTest: FunSpec({
    val aug = ModelCallAugmentationFunction("test", "test/path/to/file.onnx")

    test("should throw IllegalArgumentException if pathToModel is blank or not ending with .onnx") {
        shouldThrow<IllegalArgumentException> {
            ModelCallAugmentationFunction("test", "")
        }
        shouldThrow<IllegalArgumentException> {
            ModelCallAugmentationFunction("test", "test/path/to/file")
        }
    }

    test("when called with ModelCallRequest, returns instance of ModelCallResult") {
        val result = aug.receive(ModelCallRequest())
        result.shouldBeInstanceOf<Optional<ModelCallResult>>()
    }

    test("when called with another AugmentationEvent, returns Optional.empty") {
        data class MockEvent(val _type: String): AugmentationEvent<Nothing>(_type)
        val result = aug.receive(MockEvent("test"))
        result shouldBe Optional.empty()
    }
})