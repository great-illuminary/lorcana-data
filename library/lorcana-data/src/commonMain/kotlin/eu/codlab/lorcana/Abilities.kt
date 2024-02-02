package eu.codlab.lorcana

import eu.codlab.lorcana.abilities.Ability
import eu.codlab.lorcana.resources.Resources
import eu.codlab.lorcana.utils.AbstractLoader
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer

/**
 * Represents all the abilities that are available. It will retrieve
 * a map of name -> Ability that then can be applied to the cards
 */
object Abilities : AbstractLoader<Map<String, Ability>>(
    Resources.files.abilities,
    "abilities",
    MapSerializer(String.serializer(), Ability.serializer())
)
