package eu.codlab.lorcana.utils

import dev.icerock.moko.resources.FileResource
import eu.codlab.lorcana.utils.GithubDefinitions.dataFileContent
import eu.codlab.moko.ext.safelyReadContent
import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat

open class AbstractLoader<T>(
    private val stringFormat: StringFormat,
    private val fileResource: FileResource,
    private val file: String,
    private val serializer: KSerializer<T>
) {

    private suspend fun getStringList(): String {
        return fileResource.safelyReadContent()
    }

    suspend fun loadFromGithub(tag: String = "main"): T {
        return stringFormat.decodeFromString(serializer, dataFileContent(tag, file))
    }

    suspend fun loadFromResource(): T {
        return stringFormat.decodeFromString(serializer, getStringList())
    }
}
