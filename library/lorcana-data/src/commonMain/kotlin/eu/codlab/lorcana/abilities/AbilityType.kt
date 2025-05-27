package eu.codlab.lorcana.abilities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AbilityType {
    @SerialName("undefined")
    Undefined,

    @SerialName("static")
    Static,

    @SerialName("resolution")
    Resolution,

    @SerialName("activated")
    Activated,

    @SerialName("static-triggered")
    StaticTriggered,

    @SerialName("floating-triggered")
    FloatingTriggered,

    @SerialName("play-condition")
    PlayCondition,
}
