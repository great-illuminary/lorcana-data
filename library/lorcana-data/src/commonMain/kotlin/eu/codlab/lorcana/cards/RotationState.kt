package eu.codlab.lorcana.cards

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class RotationState {
    @SerialName("core_constructed")
    CoreConstructed,

    @SerialName("infinity_constructed")
    InfinityConstructed,

    QuestPrebuilt
}
