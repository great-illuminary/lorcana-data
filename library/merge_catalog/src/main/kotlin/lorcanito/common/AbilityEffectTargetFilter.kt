package lorcanito.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class AbilityEffectTargetFilter(
    val filter: AbilityEffectTargetFilterType,
    val value: JsonElement, // AbilityEffectTargetFilterValue or List<String>
    val characteristics: List<String> = emptyList(),
    val comparison: ResolutionComparison? = null
)

@Serializable
enum class AbilityEffectTargetFilterType {
    @SerialName("type")
    Type,

    @SerialName("zone")
    Zone,

    @SerialName("owner")
    Owner,

    @SerialName("characteristics")
    Characteristics,

    @SerialName("source")
    Source,

    @SerialName("attribute")
    Attribute,

    @SerialName("ability")
    Ability
}

@Serializable
enum class AbilityEffectTargetFilterValue {
    @SerialName("character")
    Character,

    @SerialName("discard")
    Discard,

    @SerialName("self")
    Self,

    @SerialName("play")
    Play,
}
