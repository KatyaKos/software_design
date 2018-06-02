package ru.spbau.katyakos.view

import ru.spbau.katyakos.creatures.Hook
import ru.spbau.katyakos.creatures.Player
import ru.spbau.katyakos.model.Game

interface Viewer {
    fun clear()
    fun view()
    fun draw(game: Game)
    fun draw(creature: Hook, x: Int, y: Int)
    fun draw(creature: Player, x: Int, y: Int)
    fun draw(symbol: Char, x: Int, y: Int)
}