package io.github.lm98.whdt.csv.parser

import io.github.lm98.whdt.core.hdt.model.property.PropertyValue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec

class ParserCSVTest: FunSpec({
    test("TestCsvStringa") {
        val parser = ParserCSV.createParserCSV()
        val p = parser.parsing("# paziente;età gestazionale (weeks);sex\n1;28.3;F").get(1)?.get(2)?.component4()?.get("value")
        when (p) {
            is PropertyValue.StringPropertyValue -> println(p.value)
            else -> {}
        }
    }

    test("TestCsvFile"){
        val parser = ParserCSV.createParserCSV()
        val p = parser.parsing("csvEsempio.csv")
        ParserCSV.MyToSting(p)
    }

    test("TestCsvStringa&File"){
        val parser = ParserCSV.createParserCSV()
        val p = parser.parsing("csvEsempio.csv")
        ParserCSV.MyToSting(p)
        val c =  parser.parsing("# paziente,età gestazionale (weeks),sex\n").get(1)?.get(0)?.component4()?.get("value")
        when (c) {
            is PropertyValue.StringPropertyValue -> println(c.value)
            else -> {}
        }
    }

    test("TestSizeException") {
        val parser = ParserCSV.createParserCSV()
        val exception = shouldThrow<SizeException> {
            parser.parsing("# paziente;età gestazionale (weeks);sex\n1;28.3")
        }
    }
})
