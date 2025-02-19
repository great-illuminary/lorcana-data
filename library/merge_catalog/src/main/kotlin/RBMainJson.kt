import eu.codlab.lorcana.cards.CardType
import eu.codlab.lorcana.cards.Classification
import eu.codlab.lorcana.cards.VariantRarity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RBMainJson(
    val cards: RBCards
)

@Serializable
data class RBCards(
    val characters: List<RBCard> = emptyList(),
    val locations: List<RBCard> = emptyList(),
    val items: List<RBCard> = emptyList(),
    val actions: List<RBCard> = emptyList(),
    val all: List<RBCard> = emptyList()
) {
    val cards = characters + locations + items + actions + all

    fun type(card: RBCard): CardType {
        if (null != characters.find { it.cardIdentifier == card.cardIdentifier }) return CardType.Glimmer
        if (null != locations.find { it.cardIdentifier == card.cardIdentifier }) return CardType.Location
        if (null != actions.find { it.cardIdentifier == card.cardIdentifier }) return CardType.Action
        if (null != items.find { it.cardIdentifier == card.cardIdentifier }) return CardType.Item

        throw IllegalStateException("invalid type called")
    }
}

@Serializable
data class RBCard(
    val name: String = "",
    val subtitle: String? = null,
    @SerialName("flavor_text")
    val flavorText: String? = null,
    @SerialName("card_identifier")
    val cardIdentifier: String = "",
    @SerialName("image_urls")
    val imageUrls: List<RBImageUrl>,
    @SerialName("ink_cost")
    val inkCost: Int,
    private val rarity: String,
    val willpower: Int? = null,
    val strength: Int? = null,
    val quest_value: Int? = null,
    val ink_convertible: Boolean,
    val move_cost: Int? = null,
    val author: String,
    @SerialName("subtypes")
    private val _subtypes: List<String>
) {
    val subtypes: List<String>
        get() = _subtypes.map {
            val known = Classification.entries.map {
                it.slug to it
            }.toMap()

            val found = known[it.replace(" ", "_").lowercase()]
            if (null != found) return@map found.slug
            throw IllegalStateException("Invalid $it")
        }

    val highResImage: RBImageUrl?
        get() = imageUrls.maxByOrNull { it.height }

    val actualRarity: VariantRarity?
        get() = when (rarity) {
            "COMMON" -> VariantRarity.Common
            "UNCOMMON" -> VariantRarity.Uncommon
            "RARE" -> VariantRarity.Rare
            "SUPER" -> VariantRarity.SuperRare
            "LEGENDARY" -> VariantRarity.Legendary
            "ENCHANTED" -> VariantRarity.Enchanted
            "SPECIAL" -> null
            else -> throw IllegalStateException("invalid rarity $rarity")
        }
}

@Serializable
data class RBImageUrl(
    val height: Int,
    val url: String
)
