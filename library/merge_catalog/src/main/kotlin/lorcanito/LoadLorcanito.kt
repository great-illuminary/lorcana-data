package lorcanito

import eu.codlab.files.VirtualFile
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
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive

object LoadLorcanito {
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

        it.parseToJsonElement(left)
    }

    suspend fun load(): LoadLorcanito {
        val cards = loadFromRemote()

        val temp = VirtualFile(VirtualFile.Root, "lorcanito.json")
        val actualCards = findCards(cards)?.jsonArray
        if (true) {
            temp.write(json.encodeToString(actualCards).toByteArray())
        } else {
            temp.write(json.encodeToString(cards.jsonArray).toByteArray())
        }
        println(temp.absolutePath)

        val result = json.encodeToString(actualCards)

        val mapped = json.parseToJsonElement(result)
        val finalCards = mutableListOf<LorcanitoCard>()
        mapped.jsonArray.forEach { card ->
            if (card is JsonObject) {
                finalCards.add(json.decodeFromJsonElement(card))
            } else {
                // something like $5:props:children:props:children:1:props:loading:props:cards:228
                val id = card.jsonPrimitive.content.split(":").last().toInt()
                val actualCard = finalCards[id]
                println(actualCard)
                finalCards.add(actualCard)
            }
        }

        // val finalCards: List<LorcanitoCard> = json.decodeFromString(result)

        finalCards.forEach { cardMap["${it.set}_${it.number}"] = it }

        internalCards = finalCards
        return this
    }

    /**
     * Unmap abilities or links like $5:props:children:props:children:1:props:loading:props:cards:ID:member:ID:member
     */
    @Suppress("MagicNumber")
    fun unmapLink(link: String): Triple<Int, Int, String?>? {
        if (!::internalCards.isInitialized) return null
        val cleaned =
            link.replace("\$5:props:children:props:children:1:props:loading:props:cards:", "")
        val split = cleaned.split(":")
        // println(split)
        val cardId = split[0].toInt()
        // val elementKey = split[1] - unused here, always abilities
        val subItem = split[2].toInt()
        val reference = split.getOrNull(3)

        return Triple(cardId, subItem, reference)
    }

    fun cards(): List<LorcanitoCard> {
        return internalCards
    }

    fun card(index: Int): LorcanitoCard? {
        if (!::internalCards.isInitialized) return null

        return if (index >= 0 && index < internalCards.size) {
            internalCards[index]
        } else {
            null
        }
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
