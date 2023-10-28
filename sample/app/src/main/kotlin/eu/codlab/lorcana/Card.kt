package eu.codlab.lorcana

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class Card(
    val cost: Int,
    val inkwell: Int,
    val attack: Int? = null,
    val defence: Int? = null,
    val color: Int,
    val type: String,
    val illustrator: String,
    val number: Int,
    val rarity: String,
    val languages: Map<String, CardTranslation>,
    val edition: List<Edition>,
    val actions: List<String>,
    @SerialName("set_code")
    val setCode: String,
    @SerialName("franchise_id")
    val franchiseId: Int? = null
) {
    companion object {
        fun readFromArray(array: String): List<Card> {
            return Json.decodeFromString(array)
        }
    }
}

@Serializable
data class CardTranslation(
    val name: String,
    val title: String?,
    val flavour: String?,
    val image: String?
)

@Serializable
data class Edition(
    val name: String,
    val code: String
)