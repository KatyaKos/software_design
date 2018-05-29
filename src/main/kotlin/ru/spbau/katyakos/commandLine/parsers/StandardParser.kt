package ru.spbau.katyakos.commandLine.parsers

import ru.spbau.katyakos.ast.*
import ru.spbau.katyakos.commands.CommandFactory
import ru.spbau.katyakos.commandLine.*

/***
 * Парсер для стандартных команд bash. Подробнее в README. Реализация интерфейса CLParser.
 */
class StandardParser(private val scope: Scope) : CLParser {

    override fun parse(line: String): AstNode? {
        val commandFactory = CommandFactory(scope)
        val tokenPipes = splitTokens(line)
        var node: AstNode? = null
        for (tokens in tokenPipes) {
            val nodes: List<AstNode>? = if (node == null) { null } else { listOf(node) }
            node = commandFactory.parse(tokens, nodes) ?: CommandNode(tokens)
        }
        return node
    }

    // Подменяет переменные окружения их значениями. Пример: echo $9.
    private fun replaceAssignment(line: String): String {
        var result = ""
        var isAssignment = false
        var name = ""
        (0 until line.length)
                .asSequence()
                .map { line[it] }
                .forEach {
                    if (it == '$') {
                        if (isAssignment) result += scope.getValue(name)
                        isAssignment = true
                        name = ""
                    } else if (!isAssignment) {
                        result += it
                    } else if (!(it in 'A'..'Z' || it in 'a'..'z' || it == '$')) {
                        isAssignment = false
                        result += scope.getValue(name)
                        result += it
                    } else name += it
                }
        if (isAssignment) {
            result += scope.getValue(name)
        }
        return result
    }

    // Разделяет строку по токенам, а их между собой по пайпам.
    private fun splitTokens(line: String): List<List<String>> {
        val result = mutableListOf<List<String>>()
        var tokens = mutableListOf<String>()
        var doubleQuoted = false
        var singleQuoted = false
        var word = ""
        val pipeParse: (Char) -> Unit = { c ->
            if (c == '|') {
                if (!doubleQuoted && !singleQuoted) {
                    tokens.add(replaceAssignment(word.trim()))
                    result.add(tokens.filter { w -> w.isNotEmpty() })
                    tokens = mutableListOf()
                    word = ""
                } else word += '|'
            }
        }
        val doubleParse: (Char) -> Unit = { c ->
            if (c == '"') {
                if (doubleQuoted) {
                    doubleQuoted = false
                    tokens.add(replaceAssignment(word.trim()))
                    word = ""
                } else if (!singleQuoted) doubleQuoted = true
                else word += '"'
            }
        }
        val singleParse: (Char) -> Unit = { c ->
            if (c == '\'') {
                if (singleQuoted) {
                    singleQuoted = false
                    tokens.add(word.trim())
                    word = ""
                } else if (!doubleQuoted) singleQuoted = true
                else word += '\''
            }
        }
        val spaceParse: (Char) -> Unit = { c ->
            if (c == ' ' || c == '\n') {
                if (!doubleQuoted && !singleQuoted) {
                    tokens.add(replaceAssignment(word.trim()))
                    word = ""
                } else word += c
            }
        }
        val othersParse: (Char) -> Unit = {c ->
            if (c != '|' && c != '"' && c != '\'' && c != ' ' && c != '\n') word += c
        }
        (0 until line.length)
                .asSequence()
                .map { line[it] }
                .forEach {
                    pipeParse(it)
                    doubleParse(it)
                    singleParse(it)
                    spaceParse(it)
                    othersParse(it)
                }
        tokens.add(replaceAssignment(word.trim()))
        result.add(tokens.filter { w -> w.isNotEmpty() })
        return result
    }
}