package ru.spbau.katyakos.commands.realization

import ru.spbau.katyakos.ast.AstNode
import ru.spbau.katyakos.commands.Command
import ru.spbau.katyakos.commandLine.Scope

/***
 * Присваивает значение переменной окружения.
 * Все, что после знака равенства считается знаением.
 * x=9
 * y=4
 * wc $x -> 1 1 1
 * echo $x$y -> 94
 * echo $x!$y -> 9!4
 */
class Assignment(private val scope: Scope, private val name: String, private val value: String)
    : Command(emptyList(), false) {

    override fun equals(other: Any?): Boolean {
        if (other is Assignment) {
            return scope == other.scope && name == other.name && value == other.value
        }
        return false
    }

    override fun execute(arg: AstNode) {}

    override fun execute() {
        scope.setValue(name, value)
    }

    override fun ifExit() = false
}