package ru.spbau.katyakos.commands

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import ru.spbau.katyakos.ast.*
import ru.spbau.katyakos.commandLine.Scope
import ru.spbau.katyakos.commands.realization.*

class CommandsTest {

    private val scope = Scope()
    private lateinit var factory: CommandFactory
    private val path = System.getProperty("user.dir")

    @Before
    fun setUp() {
        scope.setValue("x", "9")
        scope.setValue("y", "4")
        factory = CommandFactory(scope)
    }

    @Test
    fun catTest() {
        val cat = Cat(listOf(TextNode("$path/tmp.txt", true)))
        cat.execute()
        assertEquals("hello", cat.getResult())
    }

    @Test
    fun catErrorTest() {
        val cat = Cat(listOf(TextNode("$path/no_such_file.txt", true)))
        cat.execute()
        assertEquals("", cat.getResult())
    }

    @Test
    fun echoTest() {
        var echo = Echo(listOf(TextNode("123", true)))
        echo.execute()
        assertEquals("123", echo.getResult())
        echo = Echo(listOf(Cat(listOf(TextNode("$path/tmp.txt", true)))))
        echo.execute()
        assertEquals("hello", echo.getResult())
    }

    @Test
    fun pwdTest() {
        val pwd = Pwd()
        pwd.execute()
        assertEquals(path, pwd.getResult())
    }

    @Test
    fun wcTest() {
        var wc = Wc(listOf(TextNode("$path/tmp.txt", true)))
        wc.execute()
        assertEquals("1 1 5", wc.getResult())
        wc = Wc(listOf(Echo(listOf(TextNode("123", true)))))
        wc.execute()
        assertEquals("1 1 3", wc.getResult())
    }

    @Test
    fun assignmentTest() {
        val assignment = Assignment(scope, "z", "5")
        assignment.execute()
        assertEquals("5", scope.getValue("z"))
    }

}