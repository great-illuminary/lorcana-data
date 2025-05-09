package lorcanito

import kotlinx.serialization.Serializable
import lorcanito.common.AbilityEffectType

@Serializable
data class AbilitySubEffectRaw(
    val type: AbilityEffectType,
    val modifier: AbilityEffectTargetModifier? = null,
    val amount: Int,
    val target: String,
)
