package lorcanito.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class AbilityTrigger(
    val on: AbilityTriggerOnType,
    val `in`: AbilityTriggerInType? = null,
    val `as`: AbilityTriggerAsType? = null,
    val exclude: AbilityTriggerExclude? = null,
    val target: JsonElement? = null,
    val hasShifted: JsonElement? = null,
    val conditions: JsonElement? = null,
    val filters: JsonElement? = null,
    val cardType: String? = null // character, seems like a feature duplication from the filters
) {

}

@Serializable
enum class AbilityTriggerExclude {
    source
}

@Serializable
enum class AbilityTriggerAsType {
    @SerialName("both")
    Both,

    @SerialName("attacker")
    Attacker,

    @SerialName("defender")
    Defender
}

@Serializable
enum class AbilityTriggerInType {
    @SerialName("challenge")
    Challenge
}

@Serializable
enum class AbilityTriggerOnType {
    @SerialName("banish")
    Banish,

    @SerialName("quest")
    Quest,

    @SerialName("play")
    Play,

    @SerialName("challenge")
    Challenge,

    @SerialName("banish-another")
    BanishAnother
}