package eu.codlab.lorcana.raw

import kotlinx.serialization.Serializable

@Serializable
data class Ravensburger(
    val fr: String,
    val de: String,
    val it: String,
    val en: String
)
