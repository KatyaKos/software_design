package ru.spbau.katyakos.commandLine

import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class CLExecutorTest {

    private val scope = Scope()
    private lateinit var executor: CLExecutor
    private val path = System.getProperty("user.dir")

    @Before
    fun setUp() {
        scope.setValue("x", "9")
        scope.setValue("y", "2")
        executor = CLExecutor(scope)
    }

    @Test
    fun simpleTests() {
        assertEquals(ExecutionResult(true, ""), executor.execute("exit"))
        assertEquals(ExecutionResult(false, "123"), executor.execute("echo 123"))
        assertEquals(ExecutionResult(false, "hello"), executor.execute("cat $path/test.txt"))
        assertEquals(ExecutionResult(false, path), executor.execute("pwd"))
        assertEquals(ExecutionResult(false, "1 1 5"), executor.execute("wc $path/test.txt"))
        assertEquals(ExecutionResult(false, ""), executor.execute("z=5"))
        assertEquals("5", scope.getValue("z"))
        assertEquals(ExecutionResult(false, "9"), executor.execute("echo \$x"))
        assertEquals(ExecutionResult(false, "_____\nhello\n_____\n"), executor.execute("grep e hello"))
    }

    @Test
    fun echoTest() {
        assertEquals(ExecutionResult(false, "92"), executor.execute("echo \$x\$y"))
        assertEquals(ExecutionResult(false, "9!2"), executor.execute("echo \$x!\$y"))
        assertEquals(ExecutionResult(false, "e9!20"), executor.execute("echo e\$x!\$y0"))
        assertEquals(ExecutionResult(false, "123"), executor.execute("echo 123 | echo"))
        assertEquals(ExecutionResult(false, "hello"), executor.execute("cat $path/test.txt | echo"))
        assertEquals(ExecutionResult(false, "hey\'"), executor.execute("echo \"hey\'\""))
    }

    @Test
    fun wcTest() {
        assertEquals(ExecutionResult(false, "1 1 3"), executor.execute("echo 123 | wc"))
        assertEquals(ExecutionResult(false, "1 1 4"), executor.execute("echo \'hey\"\' | wc"))
        assertEquals(ExecutionResult(false, "1 1 5"), executor.execute("cat $path/test.txt | wc"))
    }

    private val IGNORE_WORD_AFTER_RESULT = "_____\nhello\n++you\n++pol\n_____\n_____\nHELLO\n_____\n";

    @Test
    fun grepTest() {
        assertEquals(ExecutionResult(false, IGNORE_WORD_AFTER_RESULT),
                executor.execute("grep hello grep_test.txt -i -w -A 2"))
        assertEquals(ExecutionResult(false, IGNORE_WORD_AFTER_RESULT),
                executor.execute("echo \$y | grep hello grep_test.txt -i -w -A"))
    }
}