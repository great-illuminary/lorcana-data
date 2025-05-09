package lorcanito

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import eu.codlab.lorcana.abilities.AbilityType

internal object JsonDecode {
    private val json = Json {
        prettyPrint = true
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    inline fun <reified T> decode(element: JsonElement): T {
        return json.decodeFromString(json.encodeToString(element))
    }

    fun string(it: JsonElement): String {
        return json.encodeToString(it)
    }

    fun string(it: AbilityType? = null): String? {
        return if (null != it) json.encodeToString(it) else null
    }
}