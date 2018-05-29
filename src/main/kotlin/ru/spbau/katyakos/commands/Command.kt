package ru.spbau.katyakos.commands

import ru.spbau.katyakos.ast.AstNode

/***
 * Общий класс команд. Основная функция: запустить команды из списка с их аргументами и собрать результат.
 */
abstract class Command(private val args: List<AstNode>, private val term: Boolean) : AstNode {

    override fun equals(other: Any?): Boolean {
        if (other is Command) {
            return args == other.args && term == other.term
        }
        return false
    }

    protected var executionResult: String = ""

    // Обработка аргумента с командой. Для команд без аргумента тут пустота, надо переопределить метод без аргументов.
    abstract fun execute(arg: AstNode)

    override fun ifTerminal() = term

    // Логика для команд с аргументами
    override fun execute() {
        executionResult = ""
        args.forEach { node ->
            node.execute()
            execute(node)
        }
    }

    override fun getResult(): String = executionResult
}