package eu.codlab.lorcana.abilities

import eu.codlab.tcgmapper.TranslationHolder
import kotlinx.serialization.Serializable

@Serializable
data class Ability(
    val logic: String? = null, // unused for now, provided for future versions
    val values: AbilityValues? = null,
    val title: TranslationHolder<String>? = null,
    val text: TranslationHolder<String>? = null,
    val reference: String? = null
)
