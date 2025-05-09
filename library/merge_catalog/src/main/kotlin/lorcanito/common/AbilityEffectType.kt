package lorcanito.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AbilityEffectType {
    @SerialName("scry")
    Scry,

    @SerialName("lore")
    Lore,

    @SerialName("shuffle")
    Shuffle,

    @SerialName("exert")
    Exert,

    @SerialName("discard")
    Discard,

    @SerialName("heal")
    Heal,

    @SerialName("draw")
    Draw,

    @SerialName("attribute")
    Attribute,

    @SerialName("move")
    Move,

    @SerialName("replacement")
    Replacement,

    @SerialName("play")
    Play,

    @SerialName("restriction")
    Restriction,

    @SerialName("ability")
    Ability,

    @SerialName("damage")
    Damage,

    @SerialName("target-conditional")
    TargetConditional,

    @SerialName("banish")
    Banish,
}

@Serializable
enum class AbilityEffectReplacementType {
    @SerialName("cost")
    Cost,
}
