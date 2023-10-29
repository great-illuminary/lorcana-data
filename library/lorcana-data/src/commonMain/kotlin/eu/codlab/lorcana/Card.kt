package eu.codlab.lorcana

import eu.codlab.lorcana.cards.CardThirdParty
import eu.codlab.lorcana.cards.CardTranslation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Card(
    val cost: Int = 0,
    val inkwell: Boolean = false,
    val attack: Int? = null,
    val defence: Int? = null,
    val color: Int = -1,
    val type: String = "",
    val illustrator: String = "",
    val number: Int,
    val rarity: String = "",
    val languages: Map<String, CardTranslation>,
    val edition: List<Edition> = emptyList(),
    val actions: List<String>,
    @SerialName("set_code")
    val setCode: String = "",
    @SerialName("franchise_id")
    val franchiseId: Int? = null,
    val dummy: Boolean = false,
    @SerialName("third_party")
    val thirdParty: CardThirdParty? = null
) {
    companion object {
        fun readFromArray(array: String): List<Card> {
            return Json.decodeFromString(array)
        }
    }
}

@Serializable
data class Edition(
    val name: String,
    val code: String
)
