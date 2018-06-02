package ru.spbau.katyakos.model

import ru.spbau.katyakos.artifacts.Artifact
import ru.spbau.katyakos.creatures.Creature
import ru.spbau.katyakos.movement.Action

/**
 * Реализуется логика игры и взаимодействия компонентов.
 */
object Model {

    fun run(game: Game): Boolean {
        val creatures = moveCreatures(game)
        creatures.removeIf { it.second.heal(); !it.second.isAlive() }
        game.creatures.apply { clear(); putAll(creatures) }
        applyArtifacts(game)
        if (game.isFinished()) {
            if (game.isPlayerWinner()) println("\nYOU WON")
            else println("\n YOU LOST")
            return false
        }
        return true
    }

    private fun moveCreatures(game: Game): MutableList<Pair<Pair<Int, Int>, Creature>> {
        val creatures = game.creatures.toMutableMap()
        return game.creatures.map { moveCreature(it.value, it.key, game, creatures) }.toMutableList()
    }

    private fun applyArtifacts(game: Game) {
        game.creatures.forEach { (position, creature) ->
            val artifact: Artifact? = game.artifacts[position]
            if (artifact != null) {
                creature.collectArtifact(artifact)
                game.artifacts.remove(position)
            }
        }
    }

    private fun moveCreature(creature: Creature, from: Pair<Int, Int>, game: Game,
                             creatures: MutableMap<Pair<Int, Int>, Creature>): Pair<Pair<Int, Int>, Creature> {
        val action = creature.move(from, game)
        var position = when (action) {
            Action.UP -> Pair(from.first - 1, from.second)
            Action.DOWN -> Pair(from.first + 1, from.second)
            Action.LEFT -> Pair(from.first, from.second - 1)
            Action.RIGHT -> Pair(from.first, from.second + 1)
            else -> Pair(from.first, from.second)
        }
        if (game.map.isWall(position.first, position.second)) {
            position = from
        }
        if (position != from) {
            creatures[position]?.let {
                attack(creature, it)
                position = from
            }
        }
        creatures.remove(from)
        creatures.put(position, creature)
        return Pair(position, creature)
    }

    private fun attack(attacker: Creature, target: Creature) {
        val score = Math.max(0.0, attacker.characteristics.attack -
                target.characteristics.armor * target.characteristics.luck)
        println("!! ${target.__name__()} lost $score points !!")
        target.characteristics.health -= score
    }
}