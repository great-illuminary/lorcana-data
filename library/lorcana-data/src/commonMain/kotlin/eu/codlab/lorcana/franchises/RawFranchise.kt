package eu.codlab.lorcana.franchises

import eu.codlab.tcgmapper.TranslationHolder
import kotlinx.serialization.Serializable

@Serializable
internal data class RawFranchise(
    val translations: TranslationHolder
)
