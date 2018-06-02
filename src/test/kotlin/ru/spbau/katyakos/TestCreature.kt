package ru.spbau.katyakos

import org.junit.Test
import ru.spbau.katyakos.artifacts.ArtifactFactory
import ru.spbau.katyakos.creatures.CreatureFactory
import org.junit.Assert.assertEquals

class TestCreature {
    private val creature = CreatureFactory.create(CreatureFactory.CreatureType.HOOK)
    private val artifacts = mutableListOf(ArtifactFactory.ArtifactType.KNIFE, ArtifactFactory.ArtifactType.BONNET)
            .map { ArtifactFactory.create(it) }
            .toMutableList()

    @Test
    fun artifactsUsageTest() {
        artifacts.forEachIndexed { index, artifact ->
            creature.collectArtifact(artifact)
            assertEquals(index + 1, creature.artifacts.size)
            assertEquals(0, creature.equipped.size)
        }
        artifacts.forEachIndexed { index, artifact ->
            creature.equipArtifact(artifact)
            assertEquals(artifacts.size - index - 1, creature.artifacts.size)
            assertEquals(index + 1, creature.equipped.size)
        }
        artifacts.forEachIndexed { index, artifact ->
            creature.unequipArtifact(artifact)
            assertEquals(index + 1, creature.artifacts.size)
            assertEquals(artifacts.size - index - 1, creature.equipped.size)
        }
    }
}