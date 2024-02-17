package eu.codlab.lorcana.cards

import kotlinx.serialization.Serializable

@Serializable
data class CardTranslation(
    val name: String,
    val title: String? = null,
    val flavour: String? = null
)
