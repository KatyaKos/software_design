package ru.spbau.katyakos.commandLine

import ru.spbau.katyakos.ast.AstNode

/***
 * Преобразует строку в AST через головную вершину дерева.
 */
interface CLParser {
    fun parse(line: String): AstNode?
}