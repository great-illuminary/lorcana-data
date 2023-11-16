package eu.codlab.lorcana

import dev.icerock.moko.resources.FileResource
import eu.codlab.lorcana.resources.Resources
import eu.codlab.lorcana.utils.GithubDefinitions.dataFileContent
import eu.codlab.lorcana.utils.Provider
import eu.codlab.moko.ext.safelyReadContent

enum class Set(private val fileResource: FileResource, private val fileName: String) {
    D23(Resources.files.d23, "d23"),
    TFC(Resources.files.tfc, "tfc"),
    ROTF(Resources.files.rotf, "rotf");

    private suspend fun getStringList(): String {
        return fileResource.safelyReadContent()
    }

    suspend fun loadFromGithub(tag: String = "main"): List<RawCard> {
        return Provider.json.decodeFromString(dataFileContent(tag, fileName))
    }

    suspend fun loadFromResource(): List<RawCard> {
        return Provider.json.decodeFromString(getStringList())
    }
}
