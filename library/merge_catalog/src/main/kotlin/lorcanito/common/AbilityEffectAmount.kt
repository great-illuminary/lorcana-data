package lorcanito.common

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive
import lorcanito.JsonDecode

@Serializable
data class AbilityEffectAmount(
    val amount: Int? = null,
    val dynamic: Boolean? = null,
    val filters: List<AbilityEffectTargetFilter> = emptyList()
) {
    companion object {
        fun from(amount: JsonElement): AbilityEffectAmount {
            return if (amount is JsonObject) {
                JsonDecode.decode(amount)
            } else {
                AbilityEffectAmount(amount = amount.jsonPrimitive.int)
            }
        }
    }
}
