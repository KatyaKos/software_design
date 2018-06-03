package ru.spbau.katyakos.creatures

import ru.spbau.katyakos.view.ConsolViewer
import ru.spbau.katyakos.movement.Strategy
import ru.spbau.katyakos.movement.PlayerStrategy

/**
 * Игрок, управляемый пользователем.
 */
class Player : Creature() {
    override fun __name__(): String = "Player"

    override val strategy: Strategy = PlayerStrategy()

    init {
        characteristics.health = 100.0
        characteristics.maxHealth = 100.0
        characteristics.attack = 10.0
        characteristics.armor = 2.0
        characteristics.generation = 2.0
        characteristics.luck = 0.4
    }

    override fun draw(viewer: ConsolViewer, x: Int, y: Int) = viewer.draw(this, x, y)
}