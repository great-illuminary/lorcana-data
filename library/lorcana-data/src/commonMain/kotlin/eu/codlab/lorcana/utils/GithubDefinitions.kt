package eu.codlab.lorcana.utils

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import korlibs.io.lang.FileNotFoundException

internal object GithubDefinitions {
    suspend fun dataFileContent(version: String, file: String): String {
        val url = "https://raw.githubusercontent.com/codlab/lorcana-data/$version/data/$file.json"
        val request = Provider.client.get(url)

        if (request.status.value !in 200..299) {
            throw FileNotFoundException("Impossible to find the file $file for version $version")
        }

        return request.bodyAsText()
    }
}
