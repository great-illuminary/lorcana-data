package eu.codlab.lorcana.franchises

import eu.codlab.tcgmapper.TranslationHolder
import kotlinx.serialization.Serializable

@Serializable
data class Franchise(
    val id: String,
    val translations: TranslationHolder
) {
    companion object {
        internal fun from(id: String, rawFranchise: RawFranchise) = Franchise(
            id = id,
            translations = rawFranchise.translations
        )
    }
}
