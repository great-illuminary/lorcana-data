package lorcanito.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AbilityEffectMode {
    @SerialName("bottom")
    Bottom,

    @SerialName("top")
    Top,

    @SerialName("both")
    Both,
}
