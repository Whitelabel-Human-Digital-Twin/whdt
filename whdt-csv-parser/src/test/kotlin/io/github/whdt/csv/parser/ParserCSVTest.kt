package io.github.whdt.csv.parser

import io.github.whdt.core.hdt.model.property.*
import io.kotest.assertions.fail
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class ParserCSVTest: FunSpec({
    test("Test Csv StringPropertyValue") {
        val parser = ParserCSV.createParserCSV()
        val map = parser.parsing("# paziente;età gestazionale (weeks);sex\n4;28.3;F")

        map shouldNotBe null
        map.keys shouldBe setOf("4")

        val prop = map["4"]?.get(1)?.component4()?.get("value")
        prop shouldNotBe null
        when (prop) {
            is PropertyValue.StringPropertyValue -> {prop.value shouldBe "F"}
            else ->{println("errore")}
        }
    }

    test("Test Csv IntegerPropertyValue") {
        val parser = ParserCSV.createParserCSV()
        val map = parser.parsing("# paziente;età gestazionale (weeks);sex\n4;28;F")

        map shouldNotBe null
        map.keys shouldBe setOf("4")

        val prop = map["4"]?.get(0)?.component4()?.get("value")
        prop shouldNotBe null
        when (prop) {
            is PropertyValue.IntPropertyValue -> {prop.value shouldBe 28}
            else ->{fail("errore")}
        }
    }

    test("Test Csv BooleanPropertyValue") {
        val parser = ParserCSV.createParserCSV()
        val map = parser.parsing("# paziente;età gestazionale (weeks);is live\n4;28.3;TrUe")

        map shouldNotBe null
        map.keys shouldBe setOf("4")

        val prop = map["4"]?.get(1)?.component4()?.get("value")
        prop shouldNotBe null
        when (prop) {
            is PropertyValue.BooleanPropertyValue -> {prop.value shouldBe true}
            else ->{fail("errore")}
        }
    }

    test("Test Csv LongPropertyValue") {
        val parser = ParserCSV.createParserCSV()
        val map = parser.parsing("# paziente;età gestazionale (weeks);is live\n4;28.3;100000000000043242")

        map shouldNotBe null
        map.keys shouldBe setOf("4")

        val prop = map["4"]?.get(1)?.component4()?.get("value")
        prop shouldNotBe null
        when (prop) {
            is PropertyValue.LongPropertyValue -> {prop.value shouldBe 100000000000043242}
            else ->{fail("errore")}
        }
    }

    test("Test Csv FloatPropertyValue") {
        val parser = ParserCSV.createParserCSV()
        val map = parser.parsing("# paziente;età gestazionale (weeks);is live\n4;28.3;1.424323E18")

        map shouldNotBe null
        map.keys shouldBe setOf("4")

        val prop = map["4"]?.get(1)?.component4()?.get("value")
        prop shouldNotBe null
        when (prop) {
            is PropertyValue.FloatPropertyValue -> {prop.value shouldBe 1.424323E18f}
            else ->{fail("errore")}
        }
    }

    test("TestCsvFile"){
        val parser = ParserCSV.createParserCSV()
        val p = parser.parsing("csvEsempio.csv")
        p["3"]?.size shouldBe 74
        p["4"]?.size shouldBe 74
    }

    test("TestSizeException") {
        val parser = ParserCSV.createParserCSV()
        val exception = shouldThrow<SizeException> {
            parser.parsing("# paziente;età gestazionale (weeks);sex\n1;28.3")
        }
    }
})
