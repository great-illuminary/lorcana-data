package eu.codlab.lorcana.cards

import kotlinx.serialization.SerialName

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
    Oversized,

    @SerialName("mcm_comic_con2023")
    MCMComicCon2023
}
