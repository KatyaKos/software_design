package ru.spbau.katyakos.commands

import ru.spbau.katyakos.ast.AstNode
import ru.spbau.katyakos.ast.TextNode
import ru.spbau.katyakos.commands.realization.*
import ru.spbau.katyakos.commandLine.Scope

/***
 * Парсит строки в команды, представленные в виде AstNode.
 * При добавлении новой команды сюда нужно вписать саму функцию парсера parseCommand, и добавить ее в инициализацию.
 */
class CommandFactory(private val scope: Scope) {

    private val commandParsers = mutableListOf<(List<String>, List<AstNode>?) -> Command?>()

    init {
        commandParsers.add({ tokens, _ -> parseExit(tokens) })
        commandParsers.add({ tokens, nodes -> parseEcho(tokens, nodes) })
        commandParsers.add({ tokens, _ -> parsePwd(tokens) })
        commandParsers.add({ tokens, nodes -> parseCat(tokens, nodes) })
        commandParsers.add({ tokens, nodes -> parseWc(tokens, nodes) })
        commandParsers.add({ tokens, _ -> parseAssignment(tokens) })
    }

    private fun getTextNodes(args: List<String>, term: Boolean)  = args.map { TextNode(it, term) }

    private fun isCommand(tokens: List<String>, name: String) = !tokens.isEmpty() && tokens[0] == name

    private fun parseExit(tokens: List<String>): Exit? {
        if (isCommand(tokens, "exit")) {
            return Exit()
        }
        return null
    }

    private fun parseEcho(tokens: List<String>, nodes: List<AstNode>?): Echo? {
        if (isCommand(tokens, "echo")) {
            nodes?.let { return Echo(nodes) }
            return Echo(getTextNodes(tokens.subList(1, tokens.size), true))
        }
        return null
    }

    private fun parsePwd(tokens: List<String>): Pwd? {
        if (isCommand(tokens, "pwd")) {
            return Pwd()
        }
        return null
    }

    private fun parseCat(tokens: List<String>, nodes: List<AstNode>?): Cat? {
        if (isCommand(tokens, "cat")) {
            nodes?.let { return Cat(nodes) }
            return Cat(getTextNodes(tokens.subList(1, tokens.size), true))
        }
        return null
    }

    private fun parseWc(tokens: List<String>, nodes: List<AstNode>?): Wc? {
        if (isCommand(tokens, "wc")) {
            nodes?.let { return Wc(nodes) }
            return Wc(getTextNodes(tokens.subList(1, tokens.size), true))
        }
        return null
    }

    private fun parseAssignment(tokens: List<String>): Assignment? {
        if (tokens.isEmpty() || !tokens[0].trim().contains('=')) return null
        val line = tokens[0].trim()
        val valuePos = line.indexOfFirst { !(it in 'A'..'Z' || it in 'a'..'z') }
        val assignmentPos = line.indexOfFirst { it == '=' }
        if (assignmentPos == 0 || valuePos < assignmentPos) return null
        return Assignment(scope, line.substring(0, assignmentPos), line.substring(assignmentPos + 1))
    }

    /***
     * Возвращает AstNode, представляющую соответствующую токенам.
     */
    fun parse(tokens: List<String>, nodes: List<AstNode>?): Command? {
        commandParsers.forEach { command ->
            command(tokens, nodes)?.let {
                return it
            }
        }
        return null
    }
}