package eu.codlab.lorcana.utils

import dev.icerock.moko.resources.FileResource
import eu.codlab.lorcana.utils.GithubDefinitions.dataFileContent
import eu.codlab.moko.ext.safelyReadContent
import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat

open class AbstractLoader<T>(
    private val fileResource: FileResource,
    private val file: String,
    private val serializer: KSerializer<T>
) {

    suspend fun loadFromGithub(tag: String = "main"): T {
        return Provider.yaml.decodeFromString(serializer, dataFileContent(tag, file))
    }

    suspend fun loadFromResource(): T {
        return Provider.yaml.decodeFromString(serializer, fileResource.safelyReadContent())
    }

    fun to(values: T, encoder: StringFormat = Provider.json): String {
        return encoder.encodeToString(serializer, values)
    }
}
