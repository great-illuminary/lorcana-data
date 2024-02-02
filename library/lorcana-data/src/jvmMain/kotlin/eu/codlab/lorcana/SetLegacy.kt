package eu.codlab.lorcana

import korlibs.io.async.Promise
import korlibs.io.async.launch
import korlibs.io.experimental.KorioExperimentalApi
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope

enum class SetLegacy(private val set: Set) {
    PROMOS(Set.PROMOS),
    TFC(Set.TFC),
    ROTF(Set.ROTF);

    @OptIn(DelicateCoroutinesApi::class, KorioExperimentalApi::class)
    suspend fun loadFromGithub(tag: String = "main"): Promise<List<RawCard>> {
        return Promise { resolve, reject ->
            GlobalScope.launch {
                try {
                    resolve(set.loadFromGithub(tag))
                } catch (@Suppress("TooGenericExceptionCaught") err: Throwable) {
                    reject(err)
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class, KorioExperimentalApi::class)
    suspend fun loadFromResource(): Promise<List<RawCard>> {
        return Promise { resolve, reject ->
            GlobalScope.launch {
                try {
                    resolve(set.loadFromResource())
                } catch (@Suppress("TooGenericExceptionCaught") err: Throwable) {
                    reject(err)
                }
            }
        }
    }
}
