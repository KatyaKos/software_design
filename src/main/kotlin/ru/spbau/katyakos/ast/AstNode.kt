package ru.spbau.katyakos.ast

/***
 * Вершина синтаксического дерева.
 */
interface AstNode {
    // Само выполнение
    fun execute()

    // Результат выполнения
    fun getResult(): String

    // Является ли вершина конечной, или там еще зарыта какая-нибудь команда. Пример: cat example.txt | wc
    fun ifTerminal(): Boolean

    // Нужно ли завершать работу интерпретатора.
    fun ifExit(): Boolean
}