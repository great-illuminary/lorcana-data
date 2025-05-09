package lorcanito.common

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class AbilityEffectTarget(
    val type: AbilityEffectTargetType,
    val value: JsonElement,
    val excludeSelf: JsonElement? = null,
    val filters: List<AbilityEffectTargetFilter> = emptyList()
) {
    init {
        println("attempt to set excludeSelf ${actualExcludeSelf()}")
    }

    fun actualExcludeSelf() = if (excludeSelf?.jsonPrimitive?.isString == true) {
        null
    } else {
        excludeSelf?.jsonPrimitive?.booleanOrNull
    }
}
