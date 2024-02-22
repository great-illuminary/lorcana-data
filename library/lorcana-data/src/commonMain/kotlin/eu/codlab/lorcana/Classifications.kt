package eu.codlab.lorcana

import eu.codlab.lorcana.cards.ClassificationHolder
import eu.codlab.lorcana.resources.Resources
import eu.codlab.lorcana.utils.AbstractLoader
import kotlinx.serialization.builtins.ListSerializer

/**
 * Represents all the possible classifications that are available. It will retrieve
 * a map of name -> TranslationHolder that then can be applied to the cards
 */
object Classifications : AbstractLoader<List<ClassificationHolder>>(
    Resources.files.classifications,
    "classifications",
    ListSerializer(ClassificationHolder.serializer())
)
