package ru.spbau.katyakos.model

import com.googlecode.lanterna.TextCharacter
import com.googlecode.lanterna.screen.Screen
import ru.spbau.katyakos.artifacts.Artifact
import ru.spbau.katyakos.creatures.Creature
import ru.spbau.katyakos.movement.Action
import java.util.logging.Logger

/**
 * Реализуется логика игры и взаимодействия компонентов.
 */
object Model {

    private lateinit var screen: Screen
    private val info = mutableListOf<String>()
    private val logger = Logger.getLogger(Model::class.java.name)

    fun run(game: Game, screen: Screen): List<String> {
        this.screen = screen
        info.clear()
        screen.refresh()
        val creatures = moveCreatures(game)
        creatures.removeIf { it.second.heal(); !it.second.isAlive() }
        game.creatures.apply { clear(); putAll(creatures) }
        applyArtifacts(game)
        if (game.isFinished()) {
            if (game.isPlayerWinner()) {
                info.add("YOU WON!")
                logger.info("Player won")
            } else {
                info.add("YOU LOST!")
                logger.info("Player lost")
            }
        }
        return info
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
        val action = creature.move(from, game, screen)
        var position = when (action) {
            Action.UP -> {
                logger.info("Player moved up")
                Pair(from.first - 1, from.second)
            }
            Action.DOWN -> {
                logger.info("Player moved down")
                Pair(from.first + 1, from.second)
            }
            Action.LEFT -> {
                logger.info("Player moved left")
                Pair(from.first, from.second - 1)
            }
            Action.RIGHT -> {
                logger.info("Player moved right")
                Pair(from.first, from.second + 1)
            }
            else -> Pair(from.first, from.second)
        }
        if (game.map.isWall(position.first, position.second)) {
            logger.info("Player was returned to previous position, because of the wall.")
            position = from
        }
        if (position != from) {
            creatures[position]?.let {
                logger.info("${creature.__name__()} attacks ${it.__name__()}")
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
        info.add("!! ${target.__name__()} lost $score points !!")
        logger.info("!! ${target.__name__()} lost $score points !!")
        target.characteristics.health -= score
    }
}