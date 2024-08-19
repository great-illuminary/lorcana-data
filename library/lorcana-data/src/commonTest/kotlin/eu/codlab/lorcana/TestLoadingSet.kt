package eu.codlab.lorcana

import eu.codlab.ignore.IgnoreAndroid
import eu.codlab.ignore.IgnoreJs
import eu.codlab.lorcana.buildconfig.BuildKonfig
import eu.codlab.lorcana.raw.SetDescription
import eu.codlab.platform.currentPlatform
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

class TestLoadingSet {
    @IgnoreAndroid
    @IgnoreJs
    @Suppress("SwallowedException")
    @Test
    fun testSets() = runTest {
        val lorcana = Lorcana().loadFromResources()

        SetDescription.entries.forEach {
            try {
                val content = lorcana.set(it)

                assertNotNull(content)
                assertTrue(content.cards.isNotEmpty())
                assertTrue(content.virtualCards.isNotEmpty())
            } catch (err: Throwable) {
                println("$it -> $currentPlatform")
                err.printStackTrace()
                throw NullPointerException("$currentPlatform")
            }
        }
    }

    @IgnoreAndroid
    @IgnoreJs
    @Test
    fun testLoadingSetsFromResources() = runTest {
        runTestList {
            val lorcana = Lorcana().loadFromResources()

            lorcana.set(it)
        }
    }

    @IgnoreJs
    @Test
    fun testLoadingSetsFromGithub() = runTest(timeout = 20.seconds) {
        runTestList {
            val lorcana = Lorcana().loadFromGithub(BuildKonfig.commit)

            lorcana.set(it)
        }
    }

    private suspend fun runTestList(provider: suspend (set: SetDescription) -> Set) {
        listOf(
            SetDescription.Promos to 37,
            SetDescription.TFC to 216,
            SetDescription.RotF to 216,
            SetDescription.ItI to 229,
            SetDescription.UrR to 225,
            SetDescription.SSk to 223
        ).forEach { (set, count) ->
            val cards = provider(set)

            println("managing $set")

            assertEquals(count, cards.cards.size)
        }
    }
}
