package lorcanito.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AbilityCost(
    val type: AbilityCostType,
    val amount: Int? = null
)

enum class AbilityCostType {
    @SerialName("exert")
    Exert,

    @SerialName("ink")
    Ink,

    @SerialName("banish")
    Banish,

    @SerialName("card")
    Card,
}
