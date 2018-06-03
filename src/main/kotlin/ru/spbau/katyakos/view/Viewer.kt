package ru.spbau.katyakos.view

import com.googlecode.lanterna.screen.Screen
import ru.spbau.katyakos.creatures.Hook
import ru.spbau.katyakos.creatures.Player
import ru.spbau.katyakos.model.Game

interface Viewer {
    fun clear()
    fun getScreen(): Screen
    fun draw(game: Game, events: List<String>)
    fun draw(creature: Hook, x: Int, y: Int)
    fun draw(creature: Player, x: Int, y: Int)
    fun draw(symbol: Char, x: Int, y: Int)
}