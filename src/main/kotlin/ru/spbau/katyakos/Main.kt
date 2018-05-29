package ru.spbau.katyakos

import ru.spbau.katyakos.commandLine.CLExecutor
import ru.spbau.katyakos.commandLine.Scope

/***
 * Запускает интерпретатор командной строки, описанный в README. Чтобы выйти из него - введите команду exit.
 */
fun main(args : Array<String>) {
    val executor = CLExecutor(Scope())
    executor.execute()
}