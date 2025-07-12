package eu.codlab.lorcana.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ravensburger(
    val en: String,
    val fr: String = "",
    val de: String = "",
    val it: String = "",
    val zh: String? = "",
    val ja: String? = "",
    @SerialName("culture_invariant_id")
    val cultureInvariantId: Int = 0,
    @SerialName("sort_number")
    val sortNumber: Int = 0
)
