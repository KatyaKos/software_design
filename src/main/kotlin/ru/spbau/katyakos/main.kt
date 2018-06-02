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
    val world = Game()
    world.create(WallsMap(fileName))
    world.createCreature(0, 0, CreatureFactory.create(CreatureFactory.CreatureType.PLAYER))
    world.createCreature(6, 7, CreatureFactory.create(CreatureFactory.CreatureType.HOOK))
    world.createArtifact(1, 8, ArtifactFactory.create(ArtifactFactory.ArtifactType.KNIFE))
    world.createArtifact(5, 2, ArtifactFactory.create(ArtifactFactory.ArtifactType.BONNET))
    do {
        viewer.clear()
        viewer.draw(world)
        viewer.view()
    } while (Model.run(world))
}