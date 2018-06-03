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
        scope.setValue("y", "4")
        executor = CLExecutor(scope)
    }

    @Test
    fun simpleTests() {
        assertEquals(ExecutionResult(true, ""), executor.execute("exit"))
        assertEquals(ExecutionResult(false, "123"), executor.execute("echo 123"))
        assertEquals(ExecutionResult(false, "hello"), executor.execute("cat $path/tmp.txt"))
        assertEquals(ExecutionResult(false, path), executor.execute("pwd"))
        assertEquals(ExecutionResult(false, "1 1 5"), executor.execute("wc $path/tmp.txt"))
        assertEquals(ExecutionResult(false, ""), executor.execute("z=5"))
        assertEquals("5", scope.getValue("z"))
        assertEquals(ExecutionResult(false, "9"), executor.execute("echo \$x"))
    }

    @Test
    fun echoTest() {
        assertEquals(ExecutionResult(false, "94"), executor.execute("echo \$x\$y"))
        assertEquals(ExecutionResult(false, "9!4"), executor.execute("echo \$x!\$y"))
        assertEquals(ExecutionResult(false, "e9!40"), executor.execute("echo e\$x!\$y0"))
        assertEquals(ExecutionResult(false, "123"), executor.execute("echo 123 | echo"))
        assertEquals(ExecutionResult(false, "hello"), executor.execute("cat $path/tmp.txt | echo"))
        assertEquals(ExecutionResult(false, "hey\'"), executor.execute("echo \"hey\'\""))
    }

    @Test
    fun wcTest() {
        assertEquals(ExecutionResult(false, "1 1 3"), executor.execute("echo 123 | wc"))
        assertEquals(ExecutionResult(false, "1 1 4"), executor.execute("echo \'hey\"\' | wc"))
        assertEquals(ExecutionResult(false, "1 1 5"), executor.execute("cat $path/tmp.txt | wc"))
    }
}