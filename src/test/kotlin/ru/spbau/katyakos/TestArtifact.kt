package ru.spbau.katyakos

import org.junit.Test
import ru.spbau.katyakos.artifacts.ArtifactFactory
import ru.spbau.katyakos.creatures.CreatureFactory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before

class TestArtifact {
    private val creature = CreatureFactory.create(CreatureFactory.CreatureType.HOOK)
    private val knife = ArtifactFactory.create(ArtifactFactory.ArtifactType.KNIFE)
    private val bonnet = ArtifactFactory.create(ArtifactFactory.ArtifactType.BONNET)

    @Before
    fun setUp() {
        creature.collectArtifact(knife)
        creature.collectArtifact(bonnet)
    }

    @Test
    fun usageTest() {
        creature.equipArtifact(knife)
        assertTrue(25.0 == creature.characteristics.attack)
        creature.unequipArtifact(knife)
        assertTrue(15.0 == creature.characteristics.attack)
    }
}
