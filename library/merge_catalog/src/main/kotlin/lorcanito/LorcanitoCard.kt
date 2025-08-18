package lorcanito

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class LorcanitoCard(
    val notImplemented: Boolean = false,
    val id: String,
    val missingTestCase: Boolean? = null,
    val name: String? = null,
    val title: String? = null,
    val characteristics: JsonElement, // String or List<String>,
    val text: String? = null,
    val type: String? = null,
    val abilities: JsonElement? = null, // link to another card or List<JsonElement> = emptyList(), // String or LorcanitoAbility
    val flavour: String? = null,
    val inkwell: Boolean = false,
    val color: String? = null,
    val colors: JsonElement, // String or List<String>,
    val cost: Int? = null,
    val strength: Int? = null,
    val willpower: Int? = null,
    val lore: Int? = null,
    val illustrator: String,
    val number: Int? = null,
    val set: String,
    val rarity: String,
    val moveCost: Int? = null,
    /**
     * additionalNames is either a String or String[]
     */
    val additionalNames: JsonElement? = null,
    /**
     * JsonElement is only MovementDiscount
     */
    val movementDiscounts: List<JsonElement> = emptyList()
) {
    @Suppress("ThrowsCount")
    fun actualColors(): List<String> {
        if (colors is JsonPrimitive) {
            val obtained = LoadLorcanito.unmapLink(colors.content)
                ?: throw IllegalStateException("Couldn't decode ${colors.content}")

            return LoadLorcanito.card(obtained.first)?.actualColors()
                ?: throw IllegalStateException("Couldn't find card $obtained")
        }

        if (colors is JsonArray) {
            return colors.jsonArray.map { it.jsonPrimitive.content }
        }
        throw IllegalStateException("Couldn't decode $colors")
    }

    @Suppress("ThrowsCount")
    fun actualCharacteristics(): List<String> {
        if (characteristics is JsonPrimitive) {
            val obtained = LoadLorcanito.unmapLink(characteristics.content)
                ?: throw IllegalStateException("Couldn't decode ${characteristics.content}")

            return LoadLorcanito.card(obtained.first)?.actualCharacteristics()
                ?: throw IllegalStateException("Couldn't find card $obtained")
        }

        if (characteristics is JsonArray) {
            return characteristics.jsonArray.map { it.jsonPrimitive.content }
        }

        throw IllegalStateException("Couldn't decode $characteristics")
    }

    @Suppress("ComplexMethod", "ReturnCount", "ThrowsCount")
    fun actualAbilities(): List<LorcanitoAbility> {
        if (null == abilities) return emptyList()

        if (abilities is JsonPrimitive) {
            val obtained = LoadLorcanito.unmapLink(abilities.content)
                ?: throw IllegalStateException("Couldn't decode ${abilities.content}")

            return LoadLorcanito.card(obtained.first)?.actualAbilities()
                ?: throw IllegalStateException("Couldn't find card $obtained")
        }

        return (abilities as JsonArray).mapNotNull {
            if (it is JsonObject) {
                if (it.keys.size == 1 && it.containsKey("name")) {
                    null
                } else {
                    JsonDecode.decode(it)
                }
            } else if (it is JsonPrimitive) {
                val obtained = LoadLorcanito.unmapLink(it.content)
                    ?: return@mapNotNull null

                val abilities = LoadLorcanito.card(obtained.first)?.abilities as JsonArray?
                var json = abilities?.getOrNull(obtained.second)

                obtained.third?.let {
                    json = (json as JsonObject)[obtained.third]
                }

                if (json is JsonPrimitive) {
                    val abilityUnmpapped = LoadLorcanito.unmapLink((json as JsonPrimitive).content)
                        ?: return@mapNotNull null

                    val abilities =
                        LoadLorcanito.card(abilityUnmpapped.first)?.abilities as JsonArray?
                    val newJson = abilities?.getOrNull(
                        abilityUnmpapped.second
                    )

                    newJson?.let { res -> JsonDecode.decode(res) }
                } else {
                    json?.let { res -> JsonDecode.decode(res) }
                }
            } else {
                null
            }
        }
    }
}
