package ru.spbau.katyakos.view

import com.googlecode.lanterna.TextCharacter
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import ru.spbau.katyakos.creatures.Hook
import ru.spbau.katyakos.creatures.Player
import ru.spbau.katyakos.model.Game

/**
 * Класс, ответственный за рисование всего-всего в консоли.
 */
class ConsolViewer: Viewer {

    private val field: MutableList<MutableList<Char>> = mutableListOf()
    private var info: MutableList<String> = mutableListOf()
    private val screen: Screen

    init {
        val defaultTerminalFactory = DefaultTerminalFactory()
        val terminal = defaultTerminalFactory.createTerminal()
        screen = TerminalScreen(terminal)
        screen.startScreen()
    }

    override fun getScreen(): Screen {
        return screen
    }

    override fun clear() {
        field.clear()
        info.clear()
    }

    override fun draw(symbol: Char, x: Int, y: Int) {
        if (field.getOrNull(x) == null || field[x].getOrNull(y) == null) {
            throw IndexOutOfBoundsException("Error occurred during drawing symbol $symbol on pos_x=$x, pos_y=$y")
        }
        field[x][y] = symbol
    }

    override fun draw(creature: Player, x: Int, y: Int) {
        draw('@', x, y)
        info.add("Health: ${creature.characteristics.health}/${creature.characteristics.maxHealth}")
        info.add("Health generation: ${creature.characteristics.generation}")
        info.add("Damage: ${creature.characteristics.attack})")
        info.add("Armor: ${creature.characteristics.armor}")
        info.add("Artifacts: ${creature.artifacts.joinToString { it.javaClass.simpleName }}")
        info.add("Equipped: ${creature.equipped.joinToString { it.javaClass.simpleName }}")
    }

    override fun draw(creature: Hook, x: Int, y: Int) {
        draw('H', x, y)
        info.add("Hooks Health: ${creature.characteristics.health}/${creature.characteristics.maxHealth}")
    }

    override fun draw(game: Game, events: List<String>) {
        game.map.walls.forEach { field.add(it.map { if (it) '#' else '.' }.toMutableList()) }
        game.creatures.forEach { pos, creature -> creature.draw(this, pos.first, pos.second) }
        game.artifacts.forEach { pos, artifact -> artifact.draw(this, pos.first, pos.second) }
        screen.clear()
        for (i in 0 until field.size) {
            for (j in 0 until field[i].size) {
                screen.setCharacter(j, i, TextCharacter(field[i][j]))
            }
        }
        for (i in 0 until info.size) {
            for (j in 0 until info[i].length) {
                screen.setCharacter(j, field.size + i, TextCharacter(info[i][j]))
            }
        }
        for (i in 0 until events.size) {
            for (j in 0 until events[i].length) {
                screen.setCharacter(j, field.size + info.size + i, TextCharacter(events[i][j]))
            }
        }
        screen.refresh()
    }
}
