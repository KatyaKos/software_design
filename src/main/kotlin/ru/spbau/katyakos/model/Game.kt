package ru.spbau.katyakos.model

import ru.spbau.katyakos.artifacts.Artifact
import ru.spbau.katyakos.creatures.Creature
import ru.spbau.katyakos.creatures.Player

/**
 * Хранит состояние игры: поле, существ, артефакты.
 */
class Game {
    lateinit var map: WallsMap
    val creatures: MutableMap<Pair<Int, Int>, Creature> = mutableMapOf()
    val artifacts: MutableMap<Pair<Int, Int>, Artifact> = mutableMapOf()

    fun create(map: WallsMap): Game {
        this.map = map
        return this
    }

    fun createCreature(x: Int, y: Int, creature: Creature) = creatures.put(Pair(x, y), creature)

    fun createArtifact(x: Int, y: Int, artifact: Artifact) = artifacts.put(Pair(x, y), artifact)

    fun isFinished() = creatures.size == 1 || creatures.filter { it.value is Player }.isEmpty()

    fun isPlayerWinner() = creatures.size == 1 && !creatures.filter { it.value is Player }.isEmpty()
}