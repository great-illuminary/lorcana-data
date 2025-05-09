package official

import eu.codlab.files.VirtualFile
import eu.codlab.http.Configuration
import eu.codlab.http.createClient
import eu.codlab.lorcana.config.SharedConfig
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Parameters
import korlibs.io.lang.toByteArray
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class LoadOfficialData(private val rootProject: VirtualFile) {
    private lateinit var currentToken: Result
    private val client = createClient(
        Configuration(enableLogs = true)
    ) {
        // nothing
    }
    private val json = Json {
        encodeDefaults = true
        prettyPrintIndent = "  "
        prettyPrint = true
    }

    suspend fun loadLanguages() {
        listOf("de", "en", "fr", "it").forEach {
            val content = loadLanguage(it)

            val file = VirtualFile(rootProject, "data_existing/catalog/$it/full.json")
            file.write(content.toByteArray())
        }
    }

    suspend fun loadLanguage(lang: String): String {
        val token = token()

        val body = client.get("https://api.lorcana.ravensburger.com/v2/catalog/$lang") {
            headers {
                set(
                    "Authorization",
                    "${token.token_type} ${token.access_token}"
                )
            }
        }

        return json.encodeToString(json.parseToJsonElement(body.bodyAsText()))
    }

    private suspend fun token(): Result {
        if (::currentToken.isInitialized) {
            return currentToken
        }

        val body = client.post("https://sso.ravensburger.de/token") {
            headers {
                set(
                    "Authorization",
                    "Basic ${SharedConfig.lorcana}"
                )
            }
            setBody(
                FormDataContent(Parameters.build {
                    append("grant_type", "client_credentials")
                })
            )
        }

        currentToken = body.body<Result>()
        return currentToken
    }


}

@Serializable
private data class Result(
    val access_token: String,
    val token_type: String
)