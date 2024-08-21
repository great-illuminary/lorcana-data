package eu.codlab.lorcana.cards

import kotlinx.serialization.SerialName

enum class VariantRarity {
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
    PaxUplugged2023,

    @SerialName("challenge24")
    Challenge24,

    @SerialName("worlds24")
    Worlds24,

    @SerialName("unreleased")
    Unreleased,

    @SerialName("top1")
    Top1,

    @SerialName("special_events")
    SpecialEvents,

    @SerialName("villainous")
    Villainous
}
