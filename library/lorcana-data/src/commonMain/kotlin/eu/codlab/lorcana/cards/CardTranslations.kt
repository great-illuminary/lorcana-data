package eu.codlab.lorcana.cards

import kotlinx.serialization.Serializable

@Serializable
data class CardTranslations(
    val en: CardTranslation,
    val fr: CardTranslation? = null,
    val es: CardTranslation? = null,
    val de: CardTranslation? = null,
)