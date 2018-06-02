package ru.spbau.katyakos

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.spbau.katyakos.model.WallsMap


class TestArrayMap {
    private val map: WallsMap = WallsMap("maps/small.txt")

    @Test
    fun testWallsCount() {
        var wallsCount = 0
        map.walls.forEach { it.forEach { wallsCount += if (it) 1 else 0 } }
        assertEquals(14, wallsCount)
    }
}