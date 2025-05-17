package eu.codlab.lorcana

import eu.codlab.lorcana.abilities.Ability
import eu.codlab.lorcana.cards.CardThirdParty
import eu.codlab.lorcana.cards.CardTranslations
import eu.codlab.lorcana.cards.CardType
import eu.codlab.lorcana.cards.ClassificationHolder
import eu.codlab.lorcana.cards.InkColor
import eu.codlab.lorcana.cards.VariantRarity
import eu.codlab.lorcana.franchises.Franchise
import eu.codlab.lorcana.raw.Ravensburger
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
    @Deprecated("Use colors")
    val color: InkColor = InkColor.Amber,
    val colors: List<InkColor> = emptyList(),
    val type: CardType,
    val illustrator: String = "",
    val number: Int,
    val dreamborn: String,
    val ravensburger: Ravensburger,
    val rarity: VariantRarity,
    val languages: CardTranslations,
    val abilities: List<Ability>,
    @Deprecated("set")
    @SerialName("set_code")
    val setCode: SetDescription,
    val set: SetDescription,
    val franchise: Franchise,
    @SerialName("third_party")
    val thirdParty: CardThirdParty? = null,
    @SerialName("move_cost")
    val moveCost: Int? = null,
    val classifications: List<ClassificationHolder>
) {
    @Deprecated("use abilities only")
    val actions: List<Ability> = abilities
}

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
            colors = colors,
            type = type,
            illustrator = it.illustrator ?: illustrator,
            number = it.id,
            rarity = it.rarity,
            languages = languages,
            abilities = abilities,
            setCode = set,
            set = set,
            franchise = franchise,
            thirdParty = thirdParty,
            dreamborn = it.dreamborn,
            ravensburger = it.ravensburger,
            moveCost = moveCost,
            classifications = classifications,
        )
    }
}
