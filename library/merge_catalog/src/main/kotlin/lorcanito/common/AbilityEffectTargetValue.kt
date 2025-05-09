package lorcanito.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AbilityEffectTargetValue {
    @SerialName("self")
    Self
}
