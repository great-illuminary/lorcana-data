package eu.codlab.lorcana

import eu.codlab.lorcana.franchises.RawFranchise
import eu.codlab.lorcana.resources.Resources
import eu.codlab.lorcana.utils.LorcanaConfiguration.github
import eu.codlab.tcgmapper.AbstractLoader
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer

internal object Franchises : AbstractLoader<Map<String, RawFranchise>>(
    Resources.files.franchises_yml,
    "franchises",
    MapSerializer(String.serializer(), RawFranchise.serializer()),
    github
)
