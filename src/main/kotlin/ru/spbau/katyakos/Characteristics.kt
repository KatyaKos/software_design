package ru.spbau.katyakos

/**
 * Class to store different parameters of the creature
 */
data class Characteristics(
        var health: Double = 0.0,
        var maxHealth: Double = 0.0,
        var armor: Double = 0.0,
        var attack: Double = 0.0,
        var generation: Double = 0.0,
        var luck: Double = 0.0 //from 0. to 1.
)