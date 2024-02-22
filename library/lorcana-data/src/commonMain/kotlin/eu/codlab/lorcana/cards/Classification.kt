package eu.codlab.lorcana.cards

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClassificationHolder(
    val slug: Classification,
    val en: String,
    val fr: String? = null,
    val de: String? = null
)

@Serializable
enum class Classification {
    @SerialName("song")
    Song,

    @SerialName("storyborn")
    Storyborn,

    @SerialName("dreamborn")
    Dreamborn,

    @SerialName("floodborn")
    Floodborn,

    @SerialName("princess")
    Princess,

    @SerialName("prince")
    Prince,

    @SerialName("king")
    King,

    @SerialName("queen")
    Queen,

    @SerialName("deity")
    Deity,

    @SerialName("hero")
    Hero,

    @SerialName("villain")
    Villain,

    @SerialName("ally")
    Ally,

    @SerialName("musketeer")
    Musketeer,

    @SerialName("pirate")
    Pirate,

    @SerialName("alien")
    Alien,

    @SerialName("sorcerer")
    Sorcerer,

    @SerialName("broom")
    Broom,

    @SerialName("mentor")
    Mentor,

    @SerialName("fairy")
    Fairy,

    @SerialName("captain")
    Captain,

    @SerialName("inventor")
    Inventor,

    @SerialName("dragon")
    Dragon,

    @SerialName("tigger")
    Tigger,

    @SerialName("detective")
    Detective
}
