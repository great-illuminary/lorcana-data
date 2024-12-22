package eu.codlab.lorcana

import eu.codlab.lorcana.franchises.RawFranchise
import eu.codlab.lorcana.resources.Res
import eu.codlab.lorcana.utils.LorcanaConfiguration.github
import eu.codlab.tcgmapper.AbstractLoader
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
internal object Franchises : AbstractLoader<Map<String, RawFranchise>>(
    "franchises",
    MapSerializer(String.serializer(), RawFranchise.serializer()),
    github,
    { Res.readBytes("files/franchises.yml.txt") }
)
