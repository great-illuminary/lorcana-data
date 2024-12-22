package eu.codlab.lorcana

import eu.codlab.lorcana.cards.ClassificationHolder
import eu.codlab.lorcana.resources.Res
import eu.codlab.lorcana.utils.LorcanaConfiguration.github
import eu.codlab.tcgmapper.AbstractLoader
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 * Represents all the possible classifications that are available. It will retrieve
 * a map of name -> TranslationHolder that then can be applied to the cards
 */
@OptIn(ExperimentalResourceApi::class)
object Classifications : AbstractLoader<List<ClassificationHolder>>(
    "classifications",
    ListSerializer(ClassificationHolder.serializer()),
    github,
    { Res.readBytes("files/classifications.yml.txt") }
)
