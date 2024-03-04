package eu.codlab.lorcana.cards

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Language {
    @SerialName("fr")
    Fr,

    @SerialName("en")
    En,

    @SerialName("de")
    De,

    @SerialName("it")
    It
}
