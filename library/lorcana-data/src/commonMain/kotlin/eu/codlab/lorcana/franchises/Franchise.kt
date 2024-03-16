package eu.codlab.lorcana.franchises

import eu.codlab.tcgmapper.TranslationHolder
import kotlinx.serialization.Serializable

@Serializable
data class Franchise(
    val translations: TranslationHolder<String>
)
