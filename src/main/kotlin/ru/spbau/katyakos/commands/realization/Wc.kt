package ru.spbau.katyakos.commands.realization

import ru.spbau.katyakos.ast.AstNode
import ru.spbau.katyakos.commands.Command
import java.io.File
import java.io.FileNotFoundException

/***
 * Возвращает количество строк, слов и байт в файле в общем случае.
 * Также может вернуть те же числа для строки, если использовать ее как "echo 123 | wc"
 */
class Wc(args: List<AstNode>) : Command(args, false) {

    override fun equals(other: Any?): Boolean = other is Wc && super.equals(other)

    override fun execute(arg: AstNode) {
        val result = arg.getResult()
        var linesNumber: Int
        var wordsNumber = 0
        val bytesNumber: Int
        if (arg.ifTerminal()) {
            try {
                val file = File(result)
                val lines = file.readLines()
                linesNumber = lines.size
                for (line in lines)
                    wordsNumber += split(line).size
                bytesNumber = file.readBytes().size
            } catch (e: FileNotFoundException) {
                System.err.println("Cannot find file $result")
                return
            } catch (e: Exception) {
                System.err.println("Cannot read file $result")
                return
            }
        } else {
            linesNumber = result.count { it == '\n' }
            if (!result.isEmpty() && result.last() != '\n') linesNumber += 1
            wordsNumber = split(result).size
            bytesNumber = result.length
        }
        executionResult = "$linesNumber $wordsNumber $bytesNumber"
    }

    override fun ifExit() = false

    private fun split(line: String) = line.split(" ", "\n").filterNot { s -> s.isEmpty() }
}