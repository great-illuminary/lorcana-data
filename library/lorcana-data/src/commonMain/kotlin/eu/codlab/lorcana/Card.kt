package eu.codlab.lorcana

import eu.codlab.lorcana.abilities.Ability
import eu.codlab.lorcana.cards.CardThirdParty
import eu.codlab.lorcana.cards.CardTranslation
import eu.codlab.lorcana.cards.CardType
import eu.codlab.lorcana.cards.InkColor
import eu.codlab.lorcana.cards.VariantRarity
import eu.codlab.lorcana.franchises.Franchise
import eu.codlab.lorcana.raw.SetDescription
import eu.codlab.lorcana.raw.VirtualCard
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val cost: Int = 0,
    val inkwell: Boolean = false,
    val attack: Int? = null,
    val defence: Int? = null,
    val color: InkColor = InkColor.Amber,
    val type: CardType,
    val illustrator: String = "",
    val number: Int,
    val rarity: VariantRarity,
    val languages: Map<String, CardTranslation>,
    val actions: List<Ability>,
    @SerialName("set_code")
    val setCode: SetDescription,
    @SerialName("franchise_id")
    val franchiseId: Franchise,
    @SerialName("third_party")
    val thirdParty: CardThirdParty? = null
)

fun VirtualCard.toCard(set: SetDescription): List<Card>? {
    val variations = variants.filter { it.set == set }

    if (variations.isEmpty()) return null

    return variations.map {
        Card(
            cost = cost,
            inkwell = inkwell,
            attack = attack,
            defence = defence,
            color = color,
            type = type,
            illustrator = it.illustrator ?: illustrator,
            number = it.id,
            rarity = it.rarity,
            languages = languages,
            actions = actions,
            setCode = set,
            franchiseId = franchiseId,
            thirdParty = thirdParty
        )
    }
}
