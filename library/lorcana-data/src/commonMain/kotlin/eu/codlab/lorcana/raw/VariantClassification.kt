package eu.codlab.lorcana.raw

import eu.codlab.lorcana.cards.ErratasClassification
import eu.codlab.lorcana.cards.Language
import eu.codlab.lorcana.cards.VariantRarity
import kotlinx.serialization.Serializable

@Serializable
data class VariantClassification(
    val set: SetDescription,
    val id: Int,
    val suffix: String? = null,
    val dreamborn: String,
    val ravensburger: Ravensburger,
    val rarity: VariantRarity,
    val illustrator: String? = null,
    val erratas: Map<Language, ErratasClassification>? = null
)
