package eu.codlab.lorcana.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SetItem(
    val id: Int,
    val rarity: SetItemRarity
)

enum class SetItemRarity {
    @SerialName("common")
    Common,

    @SerialName("uncommon")
    Uncommon,

    @SerialName("rare")
    Rare,

    @SerialName("super_rare")
    SuperRare,

    @SerialName("legendary")
    Legendary,

    @SerialName("enchanted")
    Enchanted,

    @SerialName("gamecon23")
    Gamecon23,

    @SerialName("d23")
    D23,

    @SerialName("d100")
    Disney100,

    @SerialName("organized_play")
    OrganizedPlay,

    @SerialName("mcm_comic_con2023")
    MCMComicCon2023,

    @SerialName("pax_unplugged2023")
    PaxUplugged2023
}
