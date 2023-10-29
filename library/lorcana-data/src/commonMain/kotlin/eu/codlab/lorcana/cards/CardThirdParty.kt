package eu.codlab.lorcana.cards

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CardThirdParty(
    @SerialName("card_market")
    val cardMarket: String? = null,
    @SerialName("tcg_player")
    val tcgPlayer: String? = null
)
