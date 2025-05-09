package lorcanito.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AbilityEffectDuration {
    @SerialName("next")
    Next,

    @SerialName("turn")
    Turn,

    @SerialName("next_turn")
    NextTurn,

    @SerialName("challenge")
    Challenge,

    @SerialName("static")
    Static
}
