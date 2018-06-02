package ru.spbau.katyakos.creatures

import ru.spbau.katyakos.Characteristics
import ru.spbau.katyakos.movement.Action
import ru.spbau.katyakos.artifacts.Artifact
import ru.spbau.katyakos.view.Drawable
import ru.spbau.katyakos.movement.Strategy
import ru.spbau.katyakos.model.Game

/**
 * Общий класс для персонжей.
 */
abstract class Creature : Drawable {
    val characteristics = Characteristics()
    abstract val strategy: Strategy
    val artifacts: MutableList<Artifact> = mutableListOf()
    val equipped: MutableList<Artifact> = mutableListOf()

    abstract fun __name__(): String

    fun move(from: Pair<Int, Int>, game: Game): Action = strategy.move(this, from, game)

    fun collectArtifact(artifact: Artifact) = artifacts.add(artifact)

    fun equipArtifact(artifact: Artifact) {
        if (artifacts.remove(artifact)) {
            artifact.equip(characteristics)
            equipped.add(artifact)
        }
    }

    fun unequipArtifact(artifact: Artifact) {
        if (equipped.remove(artifact)) {
            artifact.unequip(characteristics)
            artifacts.add(artifact)
        }
    }

    fun heal() {
        if (characteristics.health < characteristics.maxHealth) {
            characteristics.health = Math.max(0.0, Math.min(characteristics.maxHealth,
                    characteristics.health + characteristics.generation))
        }
    }

    fun isAlive() = characteristics.health > 0.0
}