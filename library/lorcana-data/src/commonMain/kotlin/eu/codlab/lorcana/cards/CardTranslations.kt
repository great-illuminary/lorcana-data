package eu.codlab.lorcana.cards

import kotlinx.serialization.Serializable

@Serializable
data class CardTranslations(
    val en: CardTranslation,
    val fr: CardTranslation? = null,
    val it: CardTranslation? = null,
    val de: CardTranslation? = null,
    val zh: CardTranslation? = null,
)
