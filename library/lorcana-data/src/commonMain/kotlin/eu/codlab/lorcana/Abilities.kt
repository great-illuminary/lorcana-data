package eu.codlab.lorcana

import eu.codlab.lorcana.abilities.Ability
import eu.codlab.lorcana.resources.Resources
import eu.codlab.lorcana.utils.GithubDefinitions.dataFileContent
import eu.codlab.lorcana.utils.Provider
import eu.codlab.moko.ext.safelyReadContent

object Abilities {

    private val fileResource = Resources.files.abilities

    private suspend fun getStringList(): String {
        return fileResource.safelyReadContent()
    }

    suspend fun loadFromGithub(tag: String = "main"): Map<String, Ability> {
        return Provider.json.decodeFromString(dataFileContent(tag, "abilities"))
    }

    suspend fun loadFromResource(): Map<String, Ability> {
        return Provider.json.decodeFromString(getStringList())
    }
}
