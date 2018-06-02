package ru.spbau.katyakos.movement

import ru.spbau.katyakos.creatures.Creature
import ru.spbau.katyakos.creatures.Player
import ru.spbau.katyakos.model.Game
import java.lang.Math.*

/**
 * Атакует врагов, ходит в направлении игрока.
 */
class HookStrategy : Strategy {

    override fun move(creature: Creature, from: Pair<Int, Int>, game: Game): Action {
        creature.artifacts.forEach { creature.equipArtifact(it) }
        var closest = Int.MAX_VALUE
        var action = Action.random()
        game.creatures.forEach { (position, enemy) ->
            if (enemy is Player) {
                val deltay = from.first - position.first
                val deltax = from.second - position.second
                val dist = abs(deltax) + abs(deltay)
                if (closest > dist) {
                    closest = dist
                    if (deltax < 0) {
                        if (!game.map.isWall(from.first, from.second + 1))
                            action = Action.RIGHT
                        else do {
                            action = Action.random()
                        } while (action == Action.RIGHT)
                    } else if (deltax > 0) {
                        if (!game.map.isWall(from.first, from.second - 1))
                            action = Action.LEFT
                        else do {
                            action = Action.random()
                        } while (action == Action.LEFT)
                    } else if (deltay < 0) {
                        if (!game.map.isWall(from.first + 1, from.second))
                            action = Action.DOWN
                        else do {
                            action = Action.random()
                        } while (action == Action.DOWN)
                    } else if (deltay > 0) {
                        if (!game.map.isWall(from.first - 1, from.second))
                            action = Action.UP
                        else do {
                            action = Action.random()
                        } while (action == Action.UP)
                    }
                }
            }
        }
        return action
    }
}