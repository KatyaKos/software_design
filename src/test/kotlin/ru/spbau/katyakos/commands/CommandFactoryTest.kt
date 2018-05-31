package ru.spbau.katyakos.commands

import org.junit.Test

import org.junit.Assert.*
import ru.spbau.katyakos.ast.*
import ru.spbau.katyakos.commandLine.Scope
import ru.spbau.katyakos.commands.realization.*

class CommandFactoryTest {

    @Test
    fun parse() {
        val scope = Scope()
        scope.setValue("x", "9")
        scope.setValue("y", "4")
        val factory = CommandFactory(scope)
        assertNull(factory.parse(emptyList(), emptyList()))
        assertEquals(Exit(), factory.parse(listOf("exit"), emptyList()))
        assertNotEquals(Exit(), factory.parse(listOf("pwd"), emptyList()))
        assertEquals(Pwd(), factory.parse(listOf("pwd"), emptyList()))
        assertEquals(Wc(listOf(TextNode("test.txt", true))), factory.parse(listOf("wc", "test.txt"), null))
        assertEquals(Echo(listOf(TextNode("123", true))), factory.parse(listOf("echo", "123"), null))
        assertEquals(Cat(listOf(TextNode("123", true))), factory.parse(listOf("cat", "123"), null))
        assertEquals(Grep(listOf(TextNode("h", true), TextNode("grep_test.txt", true))),
                factory.parse(listOf("grep", "h", "grep_test.txt"), null))
        assertEquals(Assignment(scope, "z", "5"), factory.parse(listOf("z=5"), null))
    }
}