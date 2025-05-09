package lorcanito.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AbilityEffectTargetType {
    @SerialName("player")
    Player,

    @SerialName("card")
    Card,
}
