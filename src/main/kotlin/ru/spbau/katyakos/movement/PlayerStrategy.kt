package ru.spbau.katyakos.movement

import ru.spbau.katyakos.creatures.Creature
import ru.spbau.katyakos.model.Game
import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.input.KeyType
import com.googlecode.lanterna.screen.Screen

class PlayerStrategy : Strategy {

    override fun move(creature: Creature, from: Pair<Int, Int>, game: Game, screen: Screen): Action {
        val stroke: KeyStroke = screen.readInput()
        if (stroke.keyType == KeyType.Escape) {
            return Action.NONE
        }
        return when (stroke.character) {
            'w' -> Action.UP
            'a' -> Action.LEFT
            's' -> Action.DOWN
            'd' -> Action.RIGHT
            'e' -> { try{
                creature.artifacts.getOrNull(0)?.let { creature.equipArtifact(it) }; return Action.NONE;
            } catch (e: NumberFormatException) {
                println("Error: Incorrect index!")
                return Action.NONE
            }}
            'q' -> { try{
                creature.equipped.getOrNull(0)?.let { creature.unequipArtifact(it) }; return Action.NONE;
            } catch (e: NumberFormatException) {
                println("Error: Incorrect index!")
                return Action.NONE
            }}
            else -> Action.NONE
        }
    }

}