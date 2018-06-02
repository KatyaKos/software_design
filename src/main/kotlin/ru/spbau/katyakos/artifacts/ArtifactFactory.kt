package ru.spbau.katyakos.artifacts

/**
 * Производство игровых артефактов.
 */
object ArtifactFactory {
    enum class ArtifactType {
        KNIFE,
        BONNET;
    }

    fun create(type: ArtifactType): Artifact = when (type) {
        ArtifactType.KNIFE -> Knife()
        ArtifactType.BONNET -> WarBonnet()
    }
}