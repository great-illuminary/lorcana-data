package eu.codlab.lorcana

import eu.codlab.lorcana.franchises.Franchise
import eu.codlab.lorcana.resources.Resources
import eu.codlab.lorcana.utils.GithubDefinitions.dataFileContent
import eu.codlab.lorcana.utils.Provider
import eu.codlab.moko.ext.safelyReadContent

object Franchises {

    private val fileResource = Resources.files.franchises

    private suspend fun getStringList(): String {
        return fileResource.safelyReadContent()
    }

    suspend fun loadFromGithub(tag: String = "main"): Map<String, Franchise> {
        return Provider.json.decodeFromString(dataFileContent(tag, "franchises"))
    }

    suspend fun loadFromResource(): Map<String, Franchise> {
        return Provider.json.decodeFromString(getStringList())
    }
}
