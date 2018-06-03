package ru.spbau.katyakos

import ru.spbau.katyakos.artifacts.ArtifactFactory
import ru.spbau.katyakos.creatures.CreatureFactory
import ru.spbau.katyakos.view.ConsolViewer
import ru.spbau.katyakos.model.WallsMap
import ru.spbau.katyakos.model.Game
import ru.spbau.katyakos.model.Model

fun main(args: Array<String>) {
    val fileName = "maps/small.txt"
    val viewer = ConsolViewer()
    val screen = viewer.getScreen()
    val game = Game()
    game.create(WallsMap(fileName))
    game.createCreature(0, 0, CreatureFactory.create(CreatureFactory.CreatureType.PLAYER))
    game.createCreature(6, 7, CreatureFactory.create(CreatureFactory.CreatureType.HOOK))
    game.createArtifact(1, 8, ArtifactFactory.create(ArtifactFactory.ArtifactType.KNIFE))
    game.createArtifact(5, 2, ArtifactFactory.create(ArtifactFactory.ArtifactType.BONNET))
    viewer.draw(game, listOf())
    while (!game.isFinished()) {
        viewer.clear()
        viewer.draw(game, Model.run(game, screen))
    }
}