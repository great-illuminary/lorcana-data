package eu.codlab.lorcana

import eu.codlab.lorcana.resources.Resources
import eu.codlab.moko.ext.safelyReadContent
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
        if (currentPlatform == Platform.ANDROID) {
            return@runTest
        }

        Set.entries.forEach {
            try {
                val content = it.loadFromResource()

                assertNotNull(content)
                assertTrue(content.isNotEmpty())
            } catch(err: Throwable) {
                println("$currentPlatform")
                throw NullPointerException("$currentPlatform")
            }
        }
    }

    @Test
    fun testLoadingSetsFromResources() = runTest {
        if (currentPlatform == Platform.ANDROID) {
            println("it's not possible to test against android with files at that time")
            return@runTest
        }

        listOf(
            Resources.files.d23 to 23,
            Resources.files.tfc to 216
        ).forEach { set ->
            val (file, count) = set
            val content = file.safelyReadContent()
            val cards = Card.readFromArray(content)

            assertEquals(count, cards.size)
        }
    }
}