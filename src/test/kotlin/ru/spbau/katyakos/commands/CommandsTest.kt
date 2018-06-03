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
        val cat = Cat(listOf(TextNode("$path/test.txt", true)))
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
        echo = Echo(listOf(Cat(listOf(TextNode("$path/test.txt", true)))))
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
        var wc = Wc(listOf(TextNode("$path/test.txt", true)))
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

    private val SIMPLE_RESULT = "_____\nhello\n_____\nhow\n_____\npohod\n_____\n";
    private val IGNORE_RESULT = "_____\nhow\n_____\npohod\n_____\nIHO\n_____\n";
    private val WORD_RESULT = "_____\nhello\n_____\n";
    private val AFTER_RESULT = "_____\nhow\n++sdf\n++wrt\n_____\n_____\npohod\n++op\n++IHO\n_____\n";
    private val IGNORE_WORD_RESULT = "_____\nhello\n_____\nHELLO\n_____\n";
    private val IGNORE_AFTER_RESULT = "_____\nhow\n++sdf\n++wrt\npohod\n_____\n_____\npohod\n++op\n" +
            "IHO\n_____\n_____\nIHO\n++iop\n++lok\n++HELLO\n_____\n";
    private val WORD_AFTER_RESULT = "_____\nhello\n++you\n++pol\n_____\n";
    private val IGNORE_WORD_AFTER_RESULT = "_____\nhello\n++you\n++pol\n_____\n_____\nHELLO\n_____\n";

    @Test
    fun grepTest() {
        assertEquals(SIMPLE_RESULT, grepExecuteTest(listOf("h", "grep_test.txt")))
        assertEquals(IGNORE_RESULT, grepExecuteTest(listOf("ho", "grep_test.txt", "-i")))
        assertEquals(WORD_RESULT, grepExecuteTest(listOf("hello", "grep_test.txt", "-w")))
        assertEquals(AFTER_RESULT, grepExecuteTest(listOf("ho", "grep_test.txt", "-A", "2")))
        assertEquals(IGNORE_WORD_RESULT, grepExecuteTest(listOf("hello", "grep_test.txt", "-i", "-w")))
        assertEquals(IGNORE_AFTER_RESULT, grepExecuteTest(listOf("ho", "grep_test.txt", "-i", "-A", "3")))
        assertEquals(WORD_AFTER_RESULT, grepExecuteTest(listOf("hello", "grep_test.txt", "-w", "-A", "2")))
        assertEquals(IGNORE_WORD_AFTER_RESULT, grepExecuteTest(listOf("hello", "grep_test.txt", "-i", "-w", "-A", "2")))
        assertEquals("", grepExecuteTest(listOf("privet", "grep_test.txt", "-w")))
        assertEquals("", grepExecuteTest(listOf("ipo", "grep_test.txt", "-i")))
        assertEquals("_____\nhello baby\n_____\n", grepExecuteTest(listOf("ello", "hello baby")))
        assertEquals("", grepExecuteTest(listOf("elo", "hello baby")))
    }

    private fun grepExecuteTest(args: List<String>): String {
        val grep = createGrep(args)
        grep.execute()
        return grep.getResult()
    }

    private fun createGrep(args: List<String>): Grep {
        val nodes = mutableListOf<TextNode>()
        for (arg in args)
            nodes.add(TextNode(arg, true))
        return Grep(nodes)
    }
}