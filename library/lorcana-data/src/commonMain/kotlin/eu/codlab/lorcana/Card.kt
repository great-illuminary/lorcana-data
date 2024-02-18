package eu.codlab.lorcana

import eu.codlab.lorcana.abilities.Ability
import eu.codlab.lorcana.cards.CardThirdParty
import eu.codlab.lorcana.cards.CardTranslation
import eu.codlab.lorcana.cards.InkColor
import eu.codlab.lorcana.franchises.Franchise
import eu.codlab.lorcana.raw.SetDescription
import eu.codlab.lorcana.raw.SetItemRarity
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
    val type: String = "",
    val illustrator: String = "",
    val number: Int,
    val rarity: SetItemRarity,
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
    val variations = sets[set] ?: return null

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
