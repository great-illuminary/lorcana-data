package lorcanito.common

import kotlinx.serialization.Serializable

@Serializable
data class AbilityEffectLimits(
    val bottom: Int,
    val top: Int,
    val inkwell: Int,
    val hand: Int
)