package ru.spbau.katyakos.movement

import java.util.*

enum class Action {
    LEFT,
    RIGHT,
    UP,
    DOWN,
    NONE;

    companion object {
        fun random(): Action = values()[Random().nextInt(values().size)]
    }
}