package eu.codlab.lorcana

import eu.codlab.lorcana.abilities.Ability
import eu.codlab.lorcana.resources.Res
import eu.codlab.lorcana.utils.LorcanaConfiguration.github
import eu.codlab.tcgmapper.AbstractLoader
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 * Represents all the abilities that are available. It will retrieve
 * a map of name -> Ability that then can be applied to the cards
 */
@OptIn(ExperimentalResourceApi::class)
object Abilities : AbstractLoader<Map<String, Ability>>(
    "abilities",
    MapSerializer(String.serializer(), Ability.serializer()),
    github = github,
    fileResource = { Res.readBytes("files/abilities.yml.txt") }
)