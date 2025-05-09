package lorcanito

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
data class LorcanitoCard(
    val notImplemented: Boolean = false,
    val id: String,
    val missingTestCase: Boolean? = null,
    val name: String? = null,
    val title: String? = null,
    val characteristics: List<String>,
    val text: String? = null,
    val type: String? = null,
    val abilities: List<JsonElement> = emptyList(), // String or LorcanitoAbility
    val flavour: String? = null,
    val inkwell: Boolean = false,
    val color: String? = null,
    val colors: List<String>,
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
    fun actualAbilities(): List<LorcanitoAbility> {
        return abilities.mapNotNull {
            if (it is JsonObject) {
                println("managing ${JsonDecode.string(it)}")
                JsonDecode.decode(it)
            } else {
                null
            }
        }
    }

    init {
        println("decoding abilities for $abilities")
        println("-> ${actualAbilities()}")
    }
}
