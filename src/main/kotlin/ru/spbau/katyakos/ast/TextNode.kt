package ru.spbau.katyakos.ast

/***
 * Вершина, представляющая обычный текст.
 */
class TextNode(text: String, private val term: Boolean) : AstNode {

    override fun equals(other: Any?): Boolean {
        if (other is TextNode) {
            return result == other.result && term == other.term
        }
        return false
    }

    private var result = text

    override fun execute() {}

    override fun getResult(): String = result

    override fun ifTerminal() = term

    override fun ifExit() = false
}