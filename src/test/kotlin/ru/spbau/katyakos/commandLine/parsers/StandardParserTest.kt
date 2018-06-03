package ru.spbau.katyakos.commandLine.parsers

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.spbau.katyakos.ast.TextNode
import ru.spbau.katyakos.commandLine.Scope
import ru.spbau.katyakos.commands.realization.*

class StandardParserTest {
    private val scope = Scope()
    private lateinit var parser: StandardParser
    private val path = System.getProperty("user.dir")

    @Before
    fun setUp() {
        scope.setValue("x", "9")
        scope.setValue("y", "4")
        parser = StandardParser(scope)
    }

    @Test
    fun simpleParseTests() {
        assertEquals(Exit(), parser.parse("exit"))
        assertEquals(Echo(listOf(TextNode("123", true))), parser.parse("echo 123"))
        assertEquals(Cat(listOf(TextNode("$path/test.txt", true))), parser.parse("cat $path/test.txt"))
        assertEquals(Pwd(), parser.parse("pwd"))
        assertEquals(Wc(listOf(TextNode("$path/test.txt", true))), parser.parse("wc $path/test.txt"))
        assertEquals(Assignment(scope, "z", "5"), parser.parse("z=5"))
        assertEquals(Echo(listOf(TextNode("9", true))), parser.parse("echo \$x"))
        assertEquals(Grep(listOf(TextNode("h", true), TextNode("grep_test.txt", true))),
                parser.parse("grep h grep_test.txt"))
    }

    @Test
    fun echoTest() {
        assertEquals(Echo(listOf(TextNode("94", true))),
                parser.parse("echo \$x\$y"))
        assertEquals(Echo(listOf(TextNode("9!4", true))),
                parser.parse("echo \$x!\$y"))
        assertEquals(Echo(listOf(TextNode("e9!40", true))),
                parser.parse("echo e\$x!\$y0"))
        assertEquals(Echo(listOf(Echo(listOf(TextNode("123", true))))),
                parser.parse("echo 123 | echo"))
        assertEquals(Echo(listOf(Cat(listOf(TextNode("$path/test.txt", true))))),
                parser.parse("cat $path/test.txt | echo"))
        assertEquals(Echo(listOf(TextNode("hey'", true))),
                parser.parse("echo \"hey\'\""))
    }

    @Test
    fun wcTest() {
        assertEquals(Wc(listOf(Echo(listOf(TextNode("123", true))))),
                parser.parse("echo 123 | wc"))
        assertEquals(Wc(listOf(Echo(listOf(TextNode("hey\"", true))))),
                parser.parse("echo \'hey\"\' | wc"))
        assertEquals(Wc(listOf(Cat(listOf(TextNode("$path/test.txt", true))))),
                parser.parse("cat $path/test.txt | wc"))
    }

    @Test
    fun grepTest() {
        assertEquals(Grep(listOf(TextNode("h", true), Echo(listOf(TextNode("4", true))))),
                parser.parse("echo \$y | grep h"))
    }
}