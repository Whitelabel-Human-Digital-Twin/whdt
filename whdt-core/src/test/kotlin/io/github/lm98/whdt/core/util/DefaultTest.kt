package io.github.lm98.whdt.core.util

import io.github.lm98.whdt.core.hdt.model.property.BloodPressure
import io.github.lm98.whdt.core.hdt.util.Default
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class DefaultTest: FunSpec({
    test("default usage") {
        fun <T> useDefault(def: Default<T>): T {
            return def.defaultValue()
        }

        useDefault(BloodPressure) shouldBe BloodPressure(120, 80)
    }
})