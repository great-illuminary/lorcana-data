package lorcanito.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class AbilityEffect(
    val type: AbilityEffectType,
    val replacement: AbilityEffectReplacementType? = null,
    val duration: AbilityEffectDuration? = null,
    val to: AbilityEffectTo? = null,
    val exerted: Boolean? = null,
    val amount: AbilityEffectAmount? = null, // JsonElement? = null, // AbilityEffectAmount or Int
    val mode: AbilityEffectMode? = null,
    val shouldRevealTutored: Boolean? = null,
    val attribute: AbilityEffectTargetAttribute? = null, // for instace lore
    val modifier: AbilityEffectTargetModifier? = null, // for instance add
    val target: JsonElement, // String or AbilityEffectTarget
    val limits: AbilityEffectLimits? = null,
    val tutorFilters: List<AbilityEffectTutorFilter> = emptyList()
)

@Serializable
enum class AbilityEffectTargetAttribute {
    @SerialName("lore")
    Lore,
}

@Serializable
enum class AbilityEffectTargetModifier {
    @SerialName("add")
    Add
}

@Serializable
enum class AbilityEffectTo {
    @SerialName("hand")
    Hand,

    @SerialName("inkwell")
    Inkwell
}