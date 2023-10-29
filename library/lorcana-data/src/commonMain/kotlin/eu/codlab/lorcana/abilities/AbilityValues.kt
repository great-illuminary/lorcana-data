package eu.codlab.lorcana.abilities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AbilityValues(
    @SerialName("song_cost")
    val songCost: Int? = 0,
    val cost: Int? = 0,
    val count: Int? = 0,
    val damages: Int? = 0
)
