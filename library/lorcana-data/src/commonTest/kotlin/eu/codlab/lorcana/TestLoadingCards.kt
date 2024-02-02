package eu.codlab.lorcana

import eu.codlab.files.VirtualFile
import eu.codlab.lorcana.resources.Resources
import eu.codlab.lorcana.utils.Provider
import eu.codlab.moko.ext.safelyReadContent
import eu.codlab.platform.Platform
import eu.codlab.platform.currentPlatform
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
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
            "promos" to 28,
            "tfc" to 216,
            "rotf" to 216
        ).forEach { set ->
            val (name, count) = set
            val file = VirtualFile(VirtualFile.Root, "../../data/$name.yml")
            val cards: List<RawCard> = Provider.yaml.decodeFromString(
                ListSerializer(RawCard.serializer(String.serializer(), String.serializer())),
                file.readString()
            )

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
            Resources.files.promos to 28,
            Resources.files.tfc to 216,
            Resources.files.rotf to 216
        ).forEach { set ->
            val (file, count) = set
            val content = file.safelyReadContent()
            val cards: List<RawCard> = Provider.yaml.decodeFromString(
                ListSerializer(RawCard.serializer(String.serializer(), String.serializer())),
                content
            )

            assertEquals(count, cards.size)
        }
    }
}
