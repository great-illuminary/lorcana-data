import eu.codlab.lorcana.cards.CardType
import eu.codlab.lorcana.cards.Classification
import eu.codlab.lorcana.cards.InkColor
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

    @Suppress("ReturnCount")
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
    val variants: List<RBVariantImage>? = null,
    @Deprecated("deprecated in favor of the variants field, note : for ZH & JP")
    @SerialName("image_urls")
    val imageUrls: List<RBImageUrl>? = null,
    @SerialName("magic_ink_colors")
    val magicInkColors: List<String>,
    @SerialName("ink_cost")
    val inkCost: Int,
    private val rarity: String,
    val willpower: Int? = null,
    val strength: Int? = null,
    @SerialName("quest_value")
    val questValue: Int? = null,
    @SerialName("ink_convertible")
    val inkConvertible: Boolean,
    @SerialName("move_cost")
    val moveCost: Int? = null,
    val author: String,
    @SerialName("culture_invariant_id")
    val cultureInvariantId: Int? = null,
    @SerialName("sort_number")
    val sortNumber: Int? = null,
    @SerialName("subtypes")
    private val internalSubtypes: List<String>
) {
    val subtypes: List<String>
        get() = internalSubtypes.map {
            val known = Classification.entries.associateBy { klass -> klass.slug }

            val found = known[it.replace(" ", "_").lowercase()]
            if (null != found) return@map found.slug
            throw IllegalStateException("Invalid $it")
        }

    val resImage: String
        get() = variantImage ?: highResImage
        ?: throw IllegalStateException("no image found for $cardIdentifier")

    @Deprecated("Deprecated in favor of variantImage")
    private val highResImage: String?
        get() = imageUrls?.maxByOrNull { it.height }?.url

    private val variantImage: String?
        get() = variants?.first()?.detailImageUrl

    val actualRarity: VariantRarity?
        get() = when (rarity) {
            "COMMON" -> VariantRarity.Common
            "UNCOMMON" -> VariantRarity.Uncommon
            "RARE" -> VariantRarity.Rare
            "SUPER" -> VariantRarity.SuperRare
            "LEGENDARY" -> VariantRarity.Legendary
            "ENCHANTED" -> VariantRarity.Enchanted
            "SPECIAL" -> VariantRarity.Special
            "EPIC" -> VariantRarity.Epic
            "ICONIC" -> VariantRarity.Iconic
            else -> throw IllegalStateException("invalid rarity $rarity")
        }

    val colors: List<InkColor>
        get() = magicInkColors.map {
            when (it.uppercase()) {
                "AMBER" -> InkColor.Amber
                "AMETHYST" -> InkColor.Amethyst
                "EMERALD" -> InkColor.Emerald
                "RUBY" -> InkColor.Ruby
                "SAPPHIRE" -> InkColor.Sapphire
                "STEEL" -> InkColor.Steel
                else -> throw IllegalStateException("Invalid color for $it")
            }
        }
}

@Serializable
data class RBVariantImage(
    @SerialName("variant_id")
    val variantId: String,
    @SerialName("detail_image_url")
    val detailImageUrl: String,
    @SerialName("foil_top_layer")
    val foilTopLayer: String? = null,
    @SerialName("foil_top_layer_mask_url")
    val foilTopLayerMaskUrl: String? = null
)

@Serializable
data class RBImageUrl(
    val height: Int,
    val url: String
)