package eu.codlab.lorcana

import eu.codlab.lorcana.resources.Resources
import eu.codlab.lorcana.utils.AbstractLoader
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer

object Placeholders : AbstractLoader<Map<String, String>>(
    Resources.files.placeholders_yaml,
    "placeholders_yaml",
    MapSerializer(String.serializer(), String.serializer())
)
