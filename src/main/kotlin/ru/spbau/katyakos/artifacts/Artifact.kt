package ru.spbau.katyakos.artifacts

import ru.spbau.katyakos.Characteristics
import ru.spbau.katyakos.view.Drawable

/**
 * Общий класс игровых артефактов.
 */
abstract class Artifact : Drawable {
    val characteristics = Characteristics()
    private var used: Boolean = false

    /**
     * Использование артефакта. Повышает поинты указанных в его параметрах характеристик игрока.
     */
    fun equip(params: Characteristics) {
        if (!used) {
            params.maxHealth = params.maxHealth + characteristics.maxHealth
            params.health = Math.min(params.health + characteristics.health, params.maxHealth)
            params.attack = params.attack + characteristics.attack
            params.armor = params.armor + characteristics.armor
            params.generation += characteristics.generation
            params.luck += characteristics.luck
        }
    }

    fun unequip(params: Characteristics) {
        params.maxHealth -= characteristics.maxHealth
        params.attack -= characteristics.attack
        params.armor -= characteristics.armor
        params.generation -= characteristics.generation
        params.luck -= characteristics.luck
    }
}