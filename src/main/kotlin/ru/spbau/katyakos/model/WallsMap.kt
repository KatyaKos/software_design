package ru.spbau.katyakos.model

import java.nio.file.Paths

/**
 * Карта стен-преград.
 */
class WallsMap(fileName: String) {
    val walls: List<List<Boolean>>

    init {
        walls = Paths.get(fileName).toFile().let {
            if (!it.exists()) {
                throw NoSuchFileException(it)
            }
            it.readLines().map { it.map { it == '#' }.toMutableList() }
        }
    }

    fun isWall(x: Int, y: Int): Boolean {
        return walls.getOrNull(x) == null || walls[x].getOrNull(y) == null || walls[x][y]
    }
}