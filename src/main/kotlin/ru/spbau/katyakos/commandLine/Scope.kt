package ru.spbau.katyakos.commandLine

/***
 * Хранит переменные их их значения.
 * Имя переменной: маленький и большие буквы английского алфавита.
 */
class Scope {
    private val scope = mutableMapOf<String, String>()

    override fun equals(other: Any?): Boolean {
        if (other is Scope) {
            return scope == other.scope
        }
        return false
    }

    fun getValue(name: String): String = scope.getOrDefault(name, "")

    fun setValue(name: String, value: String) {
        scope[name] = value
    }
}