package lorcanito.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class AbilityTrigger(
    @SerialName("on")
    val triggerOn: AbilityTriggerOnType,
    @SerialName("in")
    val triggerIn: AbilityTriggerInType? = null,
    @SerialName("as")
    val triggerAs: AbilityTriggerAsType? = null,
    val exclude: AbilityTriggerExclude? = null,
    val target: JsonElement? = null,
    val hasShifted: JsonElement? = null,
    val conditions: JsonElement? = null,
    val filters: JsonElement? = null,
    val cardType: String? = null
)

@Serializable
enum class AbilityTriggerExclude {
    @SerialName("source")
    Source
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
