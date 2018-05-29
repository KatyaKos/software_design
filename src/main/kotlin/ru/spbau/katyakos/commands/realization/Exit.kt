package ru.spbau.katyakos.commands.realization

import ru.spbau.katyakos.ast.AstNode
import ru.spbau.katyakos.commands.Command

/***
 * Останавливает работу интерпретатора.
 */
class Exit : Command(emptyList(),false) {

    override fun equals(other: Any?): Boolean = other is Exit && super.equals(other)

    override fun execute(arg: AstNode) {}

    override fun ifExit() = true
}