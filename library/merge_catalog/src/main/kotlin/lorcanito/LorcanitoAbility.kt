package lorcanito

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import eu.codlab.lorcana.abilities.AbilityType

@Serializable
data class LorcanitoAbility(
    val ability: String? = null, // name or ability are
    val type: AbilityType? = null,
    // val duration: AbilityEffectDuration? = null,
    // val costs: List<AbilityCost> = emptyList(),
    //val value: Int? = null,
    val text: String? = null,
    val name: String? = null, // name or ability are
    // val optional: JsonElement? = null,
    // val isPrivate: Boolean? = false,
    // val effects: List<JsonElement> = emptyList(), // List<AbilityEffect> = emptyList(),
    val additionalNames: JsonElement? = null, // String or List<String>
    // val conditions: JsonElement? = null, // $undefined or List<AbilityCondition>
    // val trigger: AbilityTrigger? = null,
    // val layer: JsonElement? = null,
    // val resolutionConditions: JsonElement? = null,
    // val responder: Responder? = null,
    // val resolveEffectsIndividually: JsonElement? = null,
    // val dependentEffects: JsonElement? = null,
    // duplication from effects
    // val gainedAbility: JsonElement? = null,
    // val target: JsonElement? = null
) {
    //TODO map the effects to AbilityEffectRaw or ...?

    init {
        // println("additional names ${actualAdditionalNames()}")
        // println("actual effects ${actualEffects()}")
        // println("actual target ${actualTarget()}")
        // println("actual resolution conditions ${actualResolutionConditions()}")
        // println("optional ${actualOptional()}")
    }

    /*
    fun actualOptional(): Boolean? {
        if (null == optional) return null

        return if (optional is JsonPrimitive) {
            optional.booleanOrNull
        } else {
            null
        }
    }

    fun actualResolutionConditions(): List<ResolutionCondition>? {
        if (null == resolutionConditions) return null
        return if (resolutionConditions is JsonObject) {
            JsonDecode.decode(resolutionConditions)
        } else {
            null
        }
    }

    fun actualTarget(): AbilityEffectTarget? {
        if (null == target) return null
        return if (target is JsonObject) {
            JsonDecode.decode(target)
        } else {
            null
        }
    }

    fun actualEffects(): List<AbilityEffectRaw> {
        return effects.mapNotNull {
            if (it is JsonObject) {
                println("attempt to decode $it")
                JsonDecode.decode(it)
            } else {
                null
            }
        }
    }

    fun actualAdditionalNames(): List<String>? {
        if (null == additionalNames) return null

        return if (additionalNames is JsonArray) {
            additionalNames.map { it.jsonPrimitive.content }
        } else {
            listOf(additionalNames.jsonPrimitive.content)
        }
    }
    */
}