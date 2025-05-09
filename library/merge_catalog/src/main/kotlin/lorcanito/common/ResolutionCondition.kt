package lorcanito.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.int
import kotlinx.serialization.json.intOrNull

@Serializable
data class ResolutionCondition(
    val type: ResolutionConditionType,
    val comparison: ResolutionComparison,
    val filters: List<AbilityEffectTargetFilter>? = null
)

@Serializable
enum class ResolutionConditionType {
    @SerialName("play")
    Play,

    @SerialName("filter")
    Filter
}

@Serializable
data class ResolutionComparison(
    val operator: Operator,
    val value: JsonElement
) {
    init {
        println("havint actualValue for comparison = ${actualValue()}")
    }

    fun actualValue() = if (value is JsonPrimitive && null != value.intOrNull) {
        Value.Int(value.int)
    } else if (value is JsonPrimitive) {
        Value.String(value.content)
    } else {
        null
    }

    sealed class Value {
        /**
         * Represent a Value of Int type
         */
        class Int(val value: kotlin.Int) : Value()

        /**
         * Represent a Value of String type
         */
        class String(val value: kotlin.String) : Value()
    }
}

@Serializable
enum class Operator {
    @SerialName("gte")
    Gte,

    @SerialName("eq")
    Eq,

    @SerialName("lte")
    Lte
}
