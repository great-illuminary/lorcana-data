package eu.codlab.lorcana

import eu.codlab.lorcana.abilities.Ability
import eu.codlab.lorcana.cards.CardThirdParty
import eu.codlab.lorcana.cards.CardTranslation
import eu.codlab.lorcana.franchises.Franchise
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenericCard<A, F>(
    val cost: Int = 0,
    val inkwell: Boolean = false,
    val attack: Int? = null,
    val defence: Int? = null,
    val color: InkColor = InkColor.Amber,
    val type: String = "",
    val illustrator: String = "",
    val number: Int,
    val rarity: String = "",
    val languages: Map<String, CardTranslation>,
    val edition: List<Edition> = emptyList(),
    val actions: List<A>,
    @SerialName("set_code")
    val setCode: String = "",
    @SerialName("franchise_id")
    val franchiseId: F,
    val dummy: Boolean = false,
    @SerialName("third_party")
    val thirdParty: CardThirdParty? = null
)

typealias RawCard = GenericCard<String, String>
typealias Card = GenericCard<Ability, Franchise>

enum class InkColor {
    @SerialName("amber")
    Amber,

    @SerialName("amethyst")
    Amethyst,

    @SerialName("emerald")
    Emerald,

    @SerialName("ruby")
    Ruby,

    @SerialName("sapphire")
    Sapphire,

    @SerialName("steel")
    Steel
}

enum class Edition {
    @SerialName("foil")
    Foil,

    @SerialName("regular")
    Regular,

    @SerialName("enchanted")
    Enchanted,

    @SerialName("promos")
    PROMOS,

    @SerialName("gencon23")
    Gencon23,

    @SerialName("gamecon23")
    Gamecon23,

    @SerialName("disney100")
    Disney100,

    @SerialName("organized_play")
    OrganizedPlay,

    @SerialName("oversized")
    Oversized
}
