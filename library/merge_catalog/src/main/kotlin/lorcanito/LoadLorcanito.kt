package lorcanito

import eu.codlab.http.Configuration
import eu.codlab.http.createClient
import eu.codlab.lorcana.raw.SetDescription
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray

class LoadLorcanito {
    private lateinit var internalCards: List<LorcanitoCard>
    private val json = Json {
        prettyPrint = true

        ignoreUnknownKeys = true
    }
    private val client = createClient(
        Configuration(enableLogs = false)
    ) { /* nothing */ }

    private val cardMap: MutableMap<String, LorcanitoCard> = mutableMapOf()

    private suspend fun loadFromRemote() = json.let {
        val url = client.get("https://db.lorcanito.com/cards")

        val right = url.bodyAsText().split("self.__next_f.push([1,\"5:")[1]
        val left = right.split("\\n\"])</script>").first()
            .replace("\\\\\\\"", "``")
            .replace("\\", "")
            .replace("\"\$5:props:children:props:children:1:props:loading:props:cards:1290\",", "")
            .replace("\"\$5:props:children:props:children:1:props:loading:props:cards:1392\",", "")
            .replace("\"\$5:props:children:props:children:1:props:loading:props:cards:1405\",", "")

        it.parseToJsonElement(left)
    }

    suspend fun load() {
        val cards = loadFromRemote()

        val actualCards = findCards(cards)?.jsonArray

        val result = json.encodeToString(actualCards)
        val finalCards: List<LorcanitoCard> = json.decodeFromString(result)

        finalCards.forEach { cardMap["${it.set}_${it.number}"] = it }

        internalCards = finalCards
    }

    fun cards(): List<LorcanitoCard> {
        return internalCards
    }

    fun card(set: SetDescription, number: Int): LorcanitoCard? {
        val set = when (set) {
            SetDescription.TFC -> "TFC"
            SetDescription.RotF -> "ROF"
            SetDescription.ItI -> "ITI"
            SetDescription.UrR -> "URR"
            SetDescription.SSk -> "SSK"
            SetDescription.P1 -> "P1"
            SetDescription.P2 -> "P2"
            SetDescription.C1 -> "C1"
            SetDescription.Worlds -> "WORLDS"
            SetDescription.D23 -> "D23"
            SetDescription.Azu -> "006"
            SetDescription.Arc -> "007"
            SetDescription.Roj -> "008"
        }

        return cardMap["${set}_$number"]
    }

    private fun findCards(element: JsonElement): JsonElement? {
        return when (element) {
            is JsonArray -> element.map { findCards(it) }.find { null != it }
            is JsonObject -> if (element.containsKey("loading")) {
                element["loading"]?.let { findCards(it) }
            } else if (element.containsKey("children")) {
                element["children"]?.let { findCards(it) }
            } else if (element.containsKey("cards") && element["cards"] is JsonArray) {
                element["cards"]
            } else {
                null
            }

            else -> null
        }
    }
}
