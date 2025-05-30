package eu.codlab.lorcana.raw

import eu.codlab.lorcana.abilities.Ability
import eu.codlab.lorcana.cards.CardThirdParty
import eu.codlab.lorcana.cards.CardTranslations
import eu.codlab.lorcana.cards.CardType
import eu.codlab.lorcana.cards.ClassificationHolder
import eu.codlab.lorcana.cards.InkColor
import eu.codlab.lorcana.franchises.Franchise
import eu.codlab.lorcana.franchises.RawFranchise
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RawVirtualCard(
    val variants: List<VariantString> = emptyList(),
    val lore: Int? = null,
    val cost: Int = 0,
    val inkwell: Boolean = false,
    @SerialName("move_cost")
    val moveCost: Int? = null,
    val attack: Int? = null,
    val defence: Int? = null,
    val colors: List<InkColor> = emptyList(),
    val type: CardType,
    val classifications: List<String> = emptyList(),
    val illustrator: String = "",
    val languages: CardTranslations,
    val abilities: List<Ability> = emptyList(),
    @SerialName("franchise_id")
    val franchiseId: String,
    @SerialName("third_party")
    val thirdParty: CardThirdParty? = null
) {
    fun variants(set: SetDescription) = variants.filter { it.set == set }

    @Suppress("TooGenericExceptionCaught")
    internal fun to(
        mapOfClassifications: Map<String, ClassificationHolder>,
        franchises: Map<String, RawFranchise>
    ) = VirtualCard(
        cost = cost,
        inkwell = inkwell,
        moveCost = moveCost,
        attack = attack,
        defence = defence,
        variants = variants.map { it.to(mapOfClassifications) },
        colors = colors,
        // made so that the deprecated value is ok for now...
        color = colors.firstOrNull() ?: InkColor.Amber,
        type = type,
        classifications = classifications.map { slug ->
            try {
                mapOfClassifications[slug]!!
            } catch (err: Throwable) {
                println("Exception thrown because $slug couldn't be mapped")
                throw err
            }
        },
        illustrator = illustrator,
        languages = languages,
        abilities = this.abilities,
        franchise = franchises[franchiseId].let { franchise ->
            if (null != franchise) {
                Franchise.from(franchiseId, franchise)
            } else {
                Franchise.from("unknown", franchises["unknown"]!!)
            }
        },
        thirdParty = thirdParty
    )
}
