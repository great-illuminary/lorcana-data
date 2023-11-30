package eu.codlab.lorcana.abilities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AbilityValues(
    @SerialName("song_cost")
    val songCost: Int? = 0,
    val cost: Int? = 0,
    val count: Int? = 0,
    val characters: Int? = 0,
    val damages: Int? = 0,
    val draw: Int? = 0,
    val cards: Int? = 0,
    val strength: Int? = 0,
    @SerialName("count_villain")
    val countVillain: Int? = 0,
    @SerialName("count_aladdin")
    val countAladdin: Int? = 0,
    @SerialName("new_cost")
    val removeMe: Int? = 0,
    @SerialName("damage")
    val removeMe2: Int? = 0
)
