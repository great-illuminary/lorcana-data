package eu.codlab.lorcana

import eu.codlab.files.VirtualFile
import eu.codlab.lorcana.resources.Resources
import eu.codlab.moko.ext.safelyReadContent
import eu.codlab.platform.Platform
import eu.codlab.platform.currentPlatform
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestLoadingCards {

    @Test
    fun testLoadingSetsFromFiles() = runTest {
        if (currentPlatform != Platform.JVM) {
            return@runTest
        }

        listOf(
            "d23" to 23,
            "tfc" to 216
        ).forEach { set ->
            val (name, count) = set
            val file = VirtualFile(VirtualFile.Root, "../../data/$name.json")
            val cards: List<RawCard> = Json.decodeFromString(file.readString())

            assertTrue(file.exists())
            assertEquals(count, cards.size)
        }
    }

    @Test
    fun testLoadingSetsFromResources() = runTest {
        if (null != listOf(Platform.ANDROID, Platform.JS).find { currentPlatform == it }) {
            println("it's not possible to test against android or js with files at that time")
            return@runTest
        }

        listOf(
            Resources.files.d23 to 23,
            Resources.files.tfc to 216
        ).forEach { set ->
            val (file, count) = set
            val content = file.safelyReadContent()
            val cards: List<RawCard> = Json.decodeFromString(content)

            assertEquals(count, cards.size)
        }
    }
}
