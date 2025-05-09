package lorcanito

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive
import lorcanito.common.AbilityEffectAmount
import lorcanito.common.AbilityEffectDuration
import lorcanito.common.AbilityEffectLimits
import lorcanito.common.AbilityEffectMode
import lorcanito.common.AbilityEffectReplacementType
import lorcanito.common.AbilityEffectTutorFilter
import lorcanito.common.AbilityEffectType

@Serializable
data class AbilityEffectRaw(
    val type: AbilityEffectType,
    val ability: AbilityEffectAbility? = null,
    val exert: Boolean? = null,
    val autoResolve: Boolean? = null,
    val restriction: AbilityEffectRestriction? = null,
    val forFree: Boolean? = null,
    val replacement: AbilityEffectReplacementType? = null,
    val duration: AbilityEffectDuration? = null,
    val to: AbilityEffectTo? = null,
    val exerted: Boolean? = null,
    val amount: JsonElement? = null, // JsonElement? = null, // AbilityEffectAmount or Int
    val mode: AbilityEffectMode? = null,
    val shouldRevealTutored: Boolean? = null,
    val attribute: AbilityEffectTargetAttribute? = null, // for instance lore
    val modifier: AbilityEffectTargetModifier? = null, // for instance add
    val subEffect: AbilitySubEffectRaw? = null,
    val target: JsonElement? = null, // String or AbilityEffectTarget
    val limits: AbilityEffectLimits? = null,
    val tutorFilters: List<AbilityEffectTutorFilter> = emptyList(),
    // sub effects when required
    val effects: List<AbilityEffectRaw>? = null,
    val fallback: List<AbilityEffectRaw>? = null
) {
    init {
        println("decoding actual amount is ${actualAmount()}")
    }

    fun actualAmount(): AbilityEffectAmount? {
        if (null == amount) return null

        return if (amount is JsonObject) {
            JsonDecode.decode(amount)
        } else {
            AbilityEffectAmount(amount = amount.jsonPrimitive.int)
        }
    }
}

@Serializable
enum class AbilityEffectAbility {
    @SerialName("rush")
    Rush,

    @SerialName("challenger")
    Challenger,

    @SerialName("ward")
    Ward,

    @SerialName("shift")
    Shift,

    @SerialName("reckless")
    Reckless,

    @SerialName("evasive")
    Evasive,

    @SerialName("support")
    Support,

}

@Serializable
enum class AbilityEffectRestriction {
    @SerialName("ready-at-start-of-turn")
    ReadyAtStartOfTurn,

    @SerialName("quest")
    Quest,
}

@Serializable
enum class AbilityEffectTargetAttribute {
    @SerialName("lore")
    Lore,

    @SerialName("strength")
    Strength
}

@Serializable
enum class AbilityEffectTargetModifier {
    @SerialName("add")
    Add,

    @SerialName("subtract")
    Subtract
}

@Serializable
enum class AbilityEffectTo {
    @SerialName("hand")
    Hand,

    @SerialName("inkwell")
    Inkwell,
}