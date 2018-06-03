package ru.spbau.katyakos.artifacts

import ru.spbau.katyakos.view.ConsolViewer

/**
 * Артефакт, повышающий уровень атаки.
 */
class Knife : Artifact() {
    init {
        characteristics.attack = 10.0
    }

    override fun draw(viewer: ConsolViewer, x: Int, y: Int) = viewer.draw('k', x, y)
}