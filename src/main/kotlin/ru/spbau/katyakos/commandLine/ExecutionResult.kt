package ru.spbau.katyakos.commandLine

/***
 * Хранит результат выполнения команд.
 * Если значение exit стоит true, значит работу интерпретатора стоит завершить.
 */
data class ExecutionResult(val exit: Boolean, val result: String)