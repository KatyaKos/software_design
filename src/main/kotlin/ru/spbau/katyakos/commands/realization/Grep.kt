package ru.spbau.katyakos.commands.realization

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default

import ru.spbau.katyakos.ast.AstNode
import ru.spbau.katyakos.commands.Command
import java.io.File

class Grep(args: List<AstNode>) : Command(args,false) {

    override fun equals(other: Any?): Boolean = other is Grep && super.equals(other)

    private var arguments = mutableListOf<String>()

    override fun execute(arg: AstNode) {
        arguments.add(arg.getResult())
    }

    override fun execute() {
        arguments.clear()
        super.execute()

        try {
            val parsedArgs = ArgParser(arguments.toTypedArray()).parseInto(::Arguments)
            val lines: List<String>
            lines = try {
                val filename = parsedArgs.files
                val file = File(filename)
                if (file.isDirectory) {
                    System.err.println("$filename is a directory")
                    return
                }
                file.readLines()
            } catch (e: Exception) {
                parsedArgs.files.lines();
            }
            val regex =
                    if (parsedArgs.ignoreCase) {
                        Regex(parsedArgs.pattern, RegexOption.IGNORE_CASE)
                    } else {
                        Regex(parsedArgs.pattern)
                    }
            execute(lines, regex, parsedArgs.linesAfter, parsedArgs.wordsMatch)
        } catch (e: Exception) {
            System.err.println("Problem with parsing arguments occurred. Please, try again." +
                    " Example: grep *patter* *filename* -i -w -A *number*")
        }
    }

    private fun execute(lines: List<String>, regex: Regex, linesAfter: Int, wordsMatch: Boolean) {
        var i = 0
        var forcePrint = 0
        var found = false
        while (i < lines.size) {
            val line = lines[i]
            var flag = false
            val words = mutableListOf<String>()
            if (wordsMatch) words.addAll(line.split("\\s+".toRegex()))
            else words.add(line)
            for (word in words) {
                val matches = regex.findAll(line)
                if (wordsMatch) {
                    for (match in matches) {
                        flag = flag || match.range.endInclusive + 1 == word.length
                    }
                } else flag = flag || matches.count() > 0
            }
            found = found || flag
            if (flag) {
                if (forcePrint > 0) executionResult += "\n" + line + "\n_____\n"
                executionResult += "_____\n" + line
                if (linesAfter == 0) executionResult += "\n"
                forcePrint = linesAfter

            } else if (forcePrint > 0) {
                executionResult += "\n++" + line
                forcePrint --
                if (forcePrint == 0) executionResult += "\n_____\n"
            }
            i += 1
        }
        if (forcePrint > 0) executionResult += "\n_____\n"
        else if (linesAfter == 0 && found) executionResult += "_____\n"
    }

    override fun ifExit() = false

    private class Arguments(parser: ArgParser) {
        val ignoreCase by parser.flagging("-i", help = "")

        val wordsMatch by parser.flagging("-w", help = "")

        val linesAfter by parser.storing("-A", help = "") { Integer.parseInt(this) }.default(0)

        val pattern by parser.positional("PATTERN", help = "")

        val files by parser.positional("FILE", help = "")
    }
}