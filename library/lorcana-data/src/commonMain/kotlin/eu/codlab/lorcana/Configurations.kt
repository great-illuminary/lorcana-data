package eu.codlab.lorcana

import eu.codlab.lorcana.cards.Language
import eu.codlab.lorcana.raw.SetDescription
import eu.codlab.lorcana.resources.Res
import eu.codlab.lorcana.utils.LorcanaConfiguration.github
import eu.codlab.tcgmapper.AbstractLoader
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Serializable
data class Configuration(
    val images: String,
    val sets: List<SetDescription>,
    val langs: List<Language>,
    val placeholders: Map<String, String>
) {
    fun image(
        set: SetDescription,
        lang: Language,
        id: Int,
        suffix: String = ""
    ) = image(set, lang.name.lowercase(), id, suffix)

    fun image(
        set: SetDescription,
        lang: String,
        id: Int,
        suffix: String = ""
    ): String {
        return images
            .replace("{set}", set.name.lowercase())
            .replace("{lang}", lang.lowercase())
            .replace("{id}", "$id")
            .replace("{suffix}", suffix)
    }
}

/**
 * Represents all the possible classifications that are available. It will retrieve
 * a map of name -> TranslationHolder that then can be applied to the cards
 */
@OptIn(ExperimentalResourceApi::class)
object Configurations : AbstractLoader<Configuration>(
    "configuration",
    Configuration.serializer(),
    github,
    { Res.readBytes("files/configuration.yml.txt") }
)
