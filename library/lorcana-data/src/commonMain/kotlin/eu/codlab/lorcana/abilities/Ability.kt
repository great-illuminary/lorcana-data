package eu.codlab.lorcana.abilities

import eu.codlab.tcgmapper.TranslationHolder
import kotlinx.serialization.Serializable

@Serializable
data class Ability(
    val type: AbilityType = AbilityType.Undefined,
    val title: TranslationHolder? = null,
    val text: TranslationHolder? = null,
)
