package eu.codlab.lorcana

import eu.codlab.lorcana.raw.SetDescription
import korlibs.io.async.Promise
import korlibs.io.async.launch
import korlibs.io.experimental.KorioExperimentalApi
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope

enum class SetLegacy(private val set: SetDescription) {
    TFC(SetDescription.TFC),
    ROTF(SetDescription.RotF),
    PROMOS(SetDescription.Promos);

    @Suppress("TooGenericExceptionCaught")
    @OptIn(DelicateCoroutinesApi::class, KorioExperimentalApi::class)
    suspend fun loadFromGithub(tag: String = "main"): Promise<Set> {
        return Promise { resolve, reject ->
            GlobalScope.launch {
                val lorcana = Lorcana().loadFromGithub(tag)
                try {
                    resolve(lorcana.set(set))
                } catch (err: Throwable) {
                    reject(err)
                }
            }
        }
    }

    @Suppress("TooGenericExceptionCaught")
    @OptIn(DelicateCoroutinesApi::class, KorioExperimentalApi::class)
    suspend fun loadFromResource(): Promise<Set> {
        return Promise { resolve, reject ->
            GlobalScope.launch {
                val lorcana = Lorcana().loadFromResources()
                try {
                    resolve(lorcana.set(set))
                } catch (err: Throwable) {
                    reject(err)
                }
            }
        }
    }
}
