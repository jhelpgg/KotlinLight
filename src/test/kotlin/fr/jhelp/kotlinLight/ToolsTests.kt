package fr.jhelp.kotlinLight

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ToolsTests {
    @Test
    fun isDictionaryStringAnyTests() {
        Assertions.assertFalse(isDictionaryStringAny(null))
        Assertions.assertFalse(isDictionaryStringAny("toto"))
        var test : Any = CommonMap<String,Any>()
        Assertions.assertTrue(isDictionaryStringAny(test))
        test = "rer"
        Assertions.assertFalse(isDictionaryStringAny(test))
        test = CommonList<String>()
        Assertions.assertFalse(isDictionaryStringAny(test))
        test = CommonMap<String, String>()
        Assertions.assertTrue(isDictionaryStringAny(test))
    }
}