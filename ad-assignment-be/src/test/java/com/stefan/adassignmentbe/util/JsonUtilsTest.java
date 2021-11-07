package com.stefan.adassignmentbe.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonUtilsTest {

    @Test
    void test_parseList() {
        String json = "[{\"id\":10,\"other\":\"test\"},{\"id\":30,\"other\":\"second\"}]";

        List<ParseTest> result = JsonUtils.parseList(json, ParseTest.class);

        assertEquals(2, result.size());
        assertEquals(10, result.get(0).id);
        assertEquals("test", result.get(0).other);
        assertEquals(30, result.get(1).id);
        assertEquals("second", result.get(1).other);
    }

    private static class ParseTest {
        public int id;
        public String other;
    }
}
