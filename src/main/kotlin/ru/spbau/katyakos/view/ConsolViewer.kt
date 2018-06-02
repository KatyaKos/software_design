package ru.spbau.katyakos.view

import ru.spbau.katyakos.creatures.Hook
import ru.spbau.katyakos.creatures.Player
import ru.spbau.katyakos.model.Game

/**
 * Класс, ответственный за рисование всего-всего в консоли.
 */
class ConsolViewer: Viewer {
    private val field: MutableList<MutableList<Char>> = mutableListOf()
    private var info: String = ""

    override fun clear() = field.clear()

    override fun view() {
        field.forEach { println(it.joinToString(separator = "")) }
        println(info)
    }

    override fun draw(symbol: Char, x: Int, y: Int) {
        if (field.getOrNull(x) == null || field[x].getOrNull(y) == null) {
            throw IndexOutOfBoundsException("Error occurred during drawing symbol $symbol on pos_x=$x, pos_y=$y")
        }
        field[x][y] = symbol
    }

    override fun draw(creature: Player, x: Int, y: Int) {
        draw('@', x, y)
        info = "Health: ${creature.characteristics.health}/${creature.characteristics.maxHealth}\t" +
                "Health generation: ${creature.characteristics.generation}\n"
        info += "Damage: ${creature.characteristics.attack}\tArmor: ${creature.characteristics.armor}\n"
        info += "Artifacts: ${creature.artifacts.joinToString { it.javaClass.simpleName }}\n"
        info += "Equipped: ${creature.equipped.joinToString { it.javaClass.simpleName }}\n"
    }

    override fun draw(creature: Hook, x: Int, y: Int) {
        draw('H', x, y)
        info += "HooksHealth: ${creature.characteristics.health}/${creature.characteristics.maxHealth}\t"
    }

    override fun draw(game: Game) {
        game.map.walls.forEach { field.add(it.map { if (it) '#' else '.' }.toMutableList()) }
        game.creatures.forEach { pos, creature -> creature.draw(this, pos.first, pos.second) }
        game.artifacts.forEach { pos, artifact -> artifact.draw(this, pos.first, pos.second) }
    }
}
