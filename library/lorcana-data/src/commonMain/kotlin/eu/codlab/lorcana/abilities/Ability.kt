package eu.codlab.lorcana.abilities

import kotlinx.serialization.Serializable

@Serializable
data class Ability(
    val logic: String? = null, // unused for now, provided for future versions
    val values: AbilityValues? = null,
    val title: TranslationHolder? = null,
    val text: TranslationHolder? = null,
    val reference: String? = null
)
