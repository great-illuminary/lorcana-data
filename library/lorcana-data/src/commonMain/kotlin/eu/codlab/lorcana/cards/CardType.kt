package eu.codlab.lorcana.cards

import kotlinx.serialization.SerialName

enum class CardType {
    @SerialName("glimmer")
    Glimmer,

    @SerialName("item")
    Item,

    @SerialName("action")
    Action,

    @SerialName("location")
    Location
}
