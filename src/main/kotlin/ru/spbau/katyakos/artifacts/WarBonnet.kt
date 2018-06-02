package ru.spbau.katyakos.artifacts

import ru.spbau.katyakos.view.ConsolViewer

class WarBonnet : Artifact() {
    init {
        characteristics.armor = 5.0
    }

    override fun draw(viewer: ConsolViewer, x: Int, y: Int) = viewer.draw('b', x, y)
}