package ru.spbau.katyakos.commands.realization

import ru.spbau.katyakos.ast.AstNode
import ru.spbau.katyakos.commands.Command

/***
 * Возвращает путь к текущей директории.
 */
class Pwd : Command(emptyList(), false) {

    override fun equals(other: Any?): Boolean = other is Pwd && super.equals(other)

    override fun execute(arg: AstNode) {}

    override fun execute() {
        executionResult = System.getProperty("user.dir")
    }

    override fun ifExit() = false
}