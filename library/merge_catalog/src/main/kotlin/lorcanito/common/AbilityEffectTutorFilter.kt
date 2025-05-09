package lorcanito.common

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class AbilityEffectTutorFilter(
    val filter: String,
    val value: JsonElement
)
