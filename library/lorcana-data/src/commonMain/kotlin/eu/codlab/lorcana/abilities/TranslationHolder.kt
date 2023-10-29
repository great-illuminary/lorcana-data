package eu.codlab.lorcana.abilities

import kotlinx.serialization.Serializable

@Serializable
data class TranslationHolder(
    val en: String,
    val fr: String? = null,
    val de: String? = null
)
