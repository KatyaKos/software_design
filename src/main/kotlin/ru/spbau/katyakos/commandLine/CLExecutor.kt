package ru.spbau.katyakos.commandLine

import ru.spbau.katyakos.commandLine.parsers.StandardParser

/***
 * Запускает и поддерживает работу интерпретатора.
 */
class CLExecutor(private val scope: Scope) {

    fun execute() {
        while(true) {
            val line = readLine() ?: break
            val result = execute(line)
            if (result.result.isNotEmpty()) println(result.result)
            if (result.exit) break
        }
    }

    fun execute(line: String): ExecutionResult {
        val parser = StandardParser(scope)
        val node = parser.parse(line) ?: return ExecutionResult(false, "")
        node.execute()
        return ExecutionResult(node.ifExit(), node.getResult())
    }
}