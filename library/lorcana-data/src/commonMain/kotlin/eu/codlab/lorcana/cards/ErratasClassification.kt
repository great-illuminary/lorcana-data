package eu.codlab.lorcana.cards

import kotlinx.serialization.Serializable

@Serializable
data class ErratasClassification(
    val classifications: List<ClassificationHolder>? = null
)
