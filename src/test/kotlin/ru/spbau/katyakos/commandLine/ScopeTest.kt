package ru.spbau.katyakos.commandLine

import org.junit.Assert.*
import org.junit.Test

class ScopeTest {
    @Test
    fun valuesTest() {
        val scope = Scope()
        assertEquals("", scope.getValue("x"))
        scope.setValue("x", "5")
        assertEquals("5", scope.getValue("x"))
        assertNotEquals("4", scope.getValue("x"))
        scope.setValue("y", "6")
        scope.setValue("z", "7")
        assertEquals("7", scope.getValue("z"))
    }
}