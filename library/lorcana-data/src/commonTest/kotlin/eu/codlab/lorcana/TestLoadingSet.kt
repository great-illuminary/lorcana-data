package eu.codlab.lorcana

import eu.codlab.platform.Platform
import eu.codlab.platform.currentPlatform
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TestLoadingSet {

    @Test
    fun testSets() = runTest {
        if (null != listOf(Platform.ANDROID, Platform.JS).find { currentPlatform == it }) {
            println("this test is disabled on android or js")
            return@runTest
        }

        Set.entries.forEach {
            try {
                val content = it.loadFromResource()

                assertNotNull(content)
                assertTrue(content.isNotEmpty())
            } catch (err: Throwable) {
                println("$currentPlatform")
                throw NullPointerException("$currentPlatform")
            }
        }
    }

    @Test
    fun testLoadingSetsFromResources() = runTest {
        if (null != listOf(Platform.ANDROID, Platform.JS).find { currentPlatform == it }) {
            println("it's not possible to test against android or js with files at that time")
            return@runTest
        }

        runTestList {
            it.loadFromResource()
        }
    }

    @Test
    fun testLoadingSetsFromGithub() = runTest {
        runTestList {
            it.loadFromGithub("main")
        }
    }

    private suspend fun runTestList(provider: suspend (set: Set) -> List<RawCard>) {
        listOf(
            Set.D23 to 23,
            Set.TFC to 216
        ).forEach { pair ->
            val (set, count) = pair
            val cards = provider(set)

            assertEquals(count, cards.size)
        }
    }
}