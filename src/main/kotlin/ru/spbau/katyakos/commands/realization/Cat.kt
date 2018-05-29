package ru.spbau.katyakos.commands.realization

import ru.spbau.katyakos.ast.AstNode
import ru.spbau.katyakos.commands.Command
import java.io.File
import java.io.FileNotFoundException

/***
 * Выводит содержимое переданного файла.
 */
class Cat(args: List<AstNode>) : Command(args,  false) {

    override fun equals(other: Any?): Boolean = other is Cat && super.equals(other)

    override fun execute(arg: AstNode) {
        val file = arg.getResult()
        try {
            executionResult += File(file).inputStream().bufferedReader().use { it.readText() }
        } catch (e: FileNotFoundException) {
            System.err.println("Cannot find file $file")
        } catch (e: Exception) {
            System.err.println("Cannot read file $file")
        }
    }

    override fun ifExit() = false

}