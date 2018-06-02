package ru.spbau.katyakos.creatures

import java.util.*

/**
 * Производство персонажей.
 */
object CreatureFactory {
    enum class CreatureType {
        HOOK,
        PLAYER;
    }

    fun create(type: CreatureType): Creature = when (type) {
        CreatureType.HOOK -> Hook()
        CreatureType.PLAYER -> Player()
    }
}