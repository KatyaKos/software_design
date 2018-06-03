package ru.spbau.katyakos.ast

import org.junit.Assert.*
import org.junit.Test

class NodesTest {

    @Test
    fun commandNodeTest() {
        var node = CommandNode(listOf("hello", "world"))
        node.execute()
        assertEquals("", node.getResult())
    }

    @Test
    fun textNodeTest() {
        val node = TextNode("hello", true)
        assertTrue(node.ifTerminal())
        assertEquals("hello", node.getResult())
    }
}