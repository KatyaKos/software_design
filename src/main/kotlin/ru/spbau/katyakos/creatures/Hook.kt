package ru.spbau.katyakos.creatures

import ru.spbau.katyakos.view.ConsolViewer
import ru.spbau.katyakos.movement.HookStrategy
import ru.spbau.katyakos.movement.Strategy

class Hook : Creature() {
    override fun __name__(): String = "Captain Hook"

    override val strategy: Strategy = HookStrategy()

    init {
        characteristics.health = 30.0
        characteristics.maxHealth = 30.0
        characteristics.attack = 15.0
        characteristics.generation = 1.0
        characteristics.luck = 0.2
    }

    override fun draw(viewer: ConsolViewer, x: Int, y: Int) = viewer.draw(this, x, y)
}