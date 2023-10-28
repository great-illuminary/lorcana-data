package eu.codlab.lorcana

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ability(
    val logic: String,
    val values: AbilityValues? = null,
    val title: TranslationHolder? = null,
    val text: TranslationHolder
)

@Serializable
data class TranslationHolder(
    val en: String,
    val fr: String? = null,
    val de: String? = null
)

@Serializable
data class AbilityValues(
    @SerialName("song_cost")
    val songCost: Int? = 0,
    val cost: Int? = 0,
    val count: Int? = 0,
    val damages: Int? = 0
)