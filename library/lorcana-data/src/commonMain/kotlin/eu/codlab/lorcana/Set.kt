package eu.codlab.lorcana

import dev.icerock.moko.resources.FileResource
import eu.codlab.lorcana.resources.Resources
import eu.codlab.moko.ext.safelyReadContent
import kotlinx.serialization.json.Json

enum class Set(private val fileResource: FileResource) {
    D23(Resources.files.d23),
    TFC(Resources.files.tfc);

    suspend fun loadFromResource(): List<Card> {
        val content = fileResource.safelyReadContent()

        return Json.decodeFromString(content)
    }
}
