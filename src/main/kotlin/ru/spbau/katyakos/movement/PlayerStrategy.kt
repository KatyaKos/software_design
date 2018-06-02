package ru.spbau.katyakos.movement

import ru.spbau.katyakos.creatures.Creature
import ru.spbau.katyakos.model.Game

class PlayerStrategy : Strategy {
    override fun move(creature: Creature, from: Pair<Int, Int>, game: Game): Action {
        val command = readLine()
        return when (command) {
            "w" -> Action.UP
            "s" -> Action.DOWN
            "a" -> Action.LEFT
            "d" -> Action.RIGHT
            else -> {
                val args = command!!.split(' ')
                if (args.size == 2) {
                    try {
                        val index = args[1].toInt() - 1
                        when (args[0]) {
                            "equip" -> creature.artifacts.getOrNull(index)?.let {
                                creature.equipArtifact(it)
                            }
                            "unequip" -> creature.equipped.getOrNull(index)?.let {
                                creature.unequipArtifact(it)
                            }
                        }
                    } catch (e: NumberFormatException) {
                        println("Error: Incorrect index!")
                    }
                }

                Action.NONE
            }
        }
    }
}