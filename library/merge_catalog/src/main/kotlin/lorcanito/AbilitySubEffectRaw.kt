package lorcanito

import lorcanito.common.AbilityEffectType
import kotlinx.serialization.Serializable

@Serializable
data class AbilitySubEffectRaw(
    val type: AbilityEffectType,
    val modifier: AbilityEffectTargetModifier? = null,
    val amount: Int,
    val target: String,
)