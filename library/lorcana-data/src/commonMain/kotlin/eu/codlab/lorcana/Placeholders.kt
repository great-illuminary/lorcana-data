package eu.codlab.lorcana

import eu.codlab.lorcana.resources.Resources
import eu.codlab.lorcana.utils.LorcanaConfiguration.github
import eu.codlab.tcgmapper.AbstractLoader
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer

object Placeholders : AbstractLoader<Map<String, String>>(
    Resources.files.placeholders,
    "placeholders",
    MapSerializer(String.serializer(), String.serializer()),
    github
)
