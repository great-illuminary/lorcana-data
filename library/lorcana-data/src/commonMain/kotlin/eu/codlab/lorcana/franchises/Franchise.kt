package eu.codlab.lorcana.franchises

import eu.codlab.lorcana.abilities.TranslationHolder
import kotlinx.serialization.Serializable

@Serializable
data class Franchise(
    val translations: TranslationHolder
)
