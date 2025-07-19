package io.github.lm98.whdt.csv.parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class TestParserCSV {

    private ParserCSV parser;

    @BeforeEach
    void setUp() {
        parser = ParserCSV.createParserCSV();
    }

    @Test
    void testParseCSVSize() {
        parser = ParserCSV.createParserCSV();
        assertEquals("1", parser.Parsing("csvEsempio.csv").get(1).get(1).component4());
    }
}
