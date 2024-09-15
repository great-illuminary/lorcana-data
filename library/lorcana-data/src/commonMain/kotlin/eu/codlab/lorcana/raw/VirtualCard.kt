package eu.codlab.lorcana.raw

import eu.codlab.lorcana.abilities.Ability
import eu.codlab.lorcana.cards.CardThirdParty
import eu.codlab.lorcana.cards.CardTranslations
import eu.codlab.lorcana.cards.CardType
import eu.codlab.lorcana.cards.ClassificationHolder
import eu.codlab.lorcana.cards.InkColor
import eu.codlab.lorcana.franchises.Franchise
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VirtualCard(
    val cost: Int = 0,
    val inkwell: Boolean = false,
    @SerialName("move_cost")
    val moveCost: Int? = null,
    val attack: Int? = null,
    val defence: Int? = null,
    val variants: List<VariantClassification> = emptyList(),
    val color: InkColor,
    val lore: Int? = null,
    val type: CardType,
    val classifications: List<ClassificationHolder> = emptyList(),
    val illustrator: String = "",
    val languages: CardTranslations,
    val actions: List<Ability> = emptyList(),
    val franchise: Franchise,
    @SerialName("third_party")
    val thirdParty: CardThirdParty? = null
) {
    fun variants(set: SetDescription) = variants.filter { it.set == set }
}
