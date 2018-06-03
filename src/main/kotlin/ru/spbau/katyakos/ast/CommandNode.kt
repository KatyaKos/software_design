package ru.spbau.katyakos.ast

import java.io.BufferedReader
import java.io.InputStreamReader

/***
 * Вершина, представляющая bash команду.
 */
class CommandNode(private val tokens: List<String>) : AstNode {

    override fun equals(other: Any?): Boolean {
        if (other is CommandNode) {
            return tokens == other.tokens
        }
        return false
    }

    private var result: String = ""

    /**
     * Выполняет запуск команды. Представленная здесь реализация для не заданных программистом команд.
     * Будет пытаться вызвать запрошенную пользователем команду через Produce.
     * В случае неудачи в поток ошибок будет сделана соответствующая запись.
     * При добавлении новых команд этот метод следует переопределить их собственной логикой.
     */
    override fun execute() {
        result = ""
        val outputBuffer = StringBuffer()
        val text = tokens.joinToString(separator=" ")
        try {
            val p = Runtime.getRuntime().exec(text)
            p.waitFor()
            val reader = BufferedReader(InputStreamReader(p.inputStream))
            while (true) {
                val line = reader.readLine() ?: break
                outputBuffer.append(line)
            }
        } catch (e: Exception) {
            e.message?.let { System.err.println("No such command. Please, try again.") }
        }
        result = outputBuffer.toString()
    }

    override fun getResult() = result

    override fun ifTerminal() = false

    override fun ifExit() = false
}