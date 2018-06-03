package ru.spbau.katyakos.movement

import com.googlecode.lanterna.screen.Screen
import ru.spbau.katyakos.creatures.Creature
import ru.spbau.katyakos.model.Game

interface Strategy {
    fun move(creature: Creature, from: Pair<Int, Int>, game: Game, screen: Screen): Action
}