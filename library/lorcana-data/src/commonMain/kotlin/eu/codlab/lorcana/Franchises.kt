package eu.codlab.lorcana

import eu.codlab.lorcana.franchises.Franchise
import eu.codlab.lorcana.resources.Resources
import eu.codlab.lorcana.utils.AbstractLoader
import eu.codlab.lorcana.utils.Provider
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer

object Franchises : AbstractLoader<Map<String, Franchise>>(
    Provider.json,
    Resources.files.franchises_json,
    "franchises_json",
    MapSerializer(String.serializer(), Franchise.serializer())
)
