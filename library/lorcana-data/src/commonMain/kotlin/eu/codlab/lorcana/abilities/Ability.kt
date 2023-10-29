package eu.codlab.lorcana.abilities

import kotlinx.serialization.Serializable

@Serializable
data class Ability(
    val logic: String,
    val values: AbilityValues? = null,
    val title: TranslationHolder? = null,
    val text: TranslationHolder
)
