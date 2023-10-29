package eu.codlab.lorcana

import dev.icerock.moko.resources.FileResource
import eu.codlab.lorcana.resources.Resources
import eu.codlab.moko.ext.safelyReadContent
import korlibs.io.async.Promise
import korlibs.io.async.launch
import korlibs.io.experimental.KorioExperimentalApi
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.serialization.json.Json

enum class SetLegacy(private val fileResource: FileResource) {
    D23(Resources.files.d23),
    TFC(Resources.files.tfc);

    @OptIn(DelicateCoroutinesApi::class, KorioExperimentalApi::class)
    suspend fun loadFromResource(): Promise<List<Card>> {
        return Promise { resolve, reject ->
            GlobalScope.launch {
                try {
                    val content = fileResource.safelyReadContent()
                    val result: List<Card> = Json.decodeFromString(content)

                    resolve(result)
                } catch (err: Throwable) {
                    reject(err)
                }
            }
        }
    }
}