package eu.codlab.lorcana

import eu.codlab.lorcana.resources.Res
import eu.codlab.lorcana.utils.LorcanaConfiguration.github
import eu.codlab.tcgmapper.AbstractLoader
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
object Placeholders : AbstractLoader<Map<String, String>>(
    "placeholders",
    MapSerializer(String.serializer(), String.serializer()),
    github,
    fileResource = { Res.readBytes("files/placeholders.yml.txt") }
)
