package eu.codlab.lorcana

import eu.codlab.files.VirtualFile
import eu.codlab.ignore.IgnoreAndroid
import eu.codlab.ignore.IgnoreJs
import eu.codlab.lorcana.raw.RawVirtualCard
import eu.codlab.lorcana.resources.Res
import eu.codlab.platform.Platform
import eu.codlab.platform.currentPlatform
import eu.codlab.tcgmapper.Provider
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.compose.resources.ExperimentalResourceApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestLoadingCards {
    @IgnoreJs
    @Test
    fun testLoadingSetsFromFiles() = runTest {
        if (currentPlatform != Platform.JVM) {
            return@runTest
        }

        listOf(
            "tfc" to 204,
            "rotf" to 204,
            "azu" to 204
        ).forEach { set ->
            val (name, count) = set
            val file = VirtualFile(VirtualFile.Root, "../../data/$name.yml")

            val cards: List<RawVirtualCard> = Provider.yaml.decodeFromString(
                ListSerializer(RawVirtualCard.serializer()),
                file.readString()
            )

            assertTrue(file.exists())
            assertEquals(count, cards.size)
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @IgnoreAndroid
    @IgnoreJs
    @Test
    fun testLoadingSetsFromResources() = runTest {
        listOf(
            "tfc" to 204,
            "rotf" to 204
        ).forEach { set ->
            val (file, count) = set
            val readFile = Res.readBytes("files/${file}.yml.txt")
            val content = io.ktor.utils.io.core.String(readFile)
            val cards: List<RawVirtualCard> = Provider.yaml.decodeFromString(
                ListSerializer(
                    RawVirtualCard.serializer()
                ),
                content
            )

            assertEquals(count, cards.size)
        }
    }
}
