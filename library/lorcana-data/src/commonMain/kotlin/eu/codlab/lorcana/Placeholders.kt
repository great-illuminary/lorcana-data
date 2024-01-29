package eu.codlab.lorcana

import eu.codlab.lorcana.resources.Resources
import eu.codlab.lorcana.utils.AbstractLoader
import eu.codlab.lorcana.utils.Provider
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer

object Placeholders : AbstractLoader<Map<String, String>>(
    Provider.json,
    Resources.files.placeholders_json,
    "placeholders_json",
    MapSerializer(String.serializer(), String.serializer())
)
