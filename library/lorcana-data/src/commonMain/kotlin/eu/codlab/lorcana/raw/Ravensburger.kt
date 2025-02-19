package eu.codlab.lorcana.raw

import kotlinx.serialization.Serializable

@Serializable
data class Ravensburger(
    val en: String,
    val fr: String,
    val de: String,
    val it: String,
    val zh: String? = "",
)
