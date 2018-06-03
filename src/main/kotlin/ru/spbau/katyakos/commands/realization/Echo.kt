package ru.spbau.katyakos.commands.realization

import ru.spbau.katyakos.ast.AstNode
import ru.spbau.katyakos.commands.Command

/***
 * Выводит аргументы с подстановкой значений при необходимости.
 */
class Echo(args: List<AstNode>) : Command(args,false) {

    override fun equals(other: Any?): Boolean = other is Echo && super.equals(other)

    override fun execute(arg: AstNode) {
        executionResult += arg.getResult()
    }

    override fun ifExit() = false
}