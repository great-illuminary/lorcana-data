package eu.codlab.lorcana

import eu.codlab.files.VirtualFile
import korlibs.io.async.suspendTest
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestLoadingCards {

    @Test
    fun `test loading the set`() = suspendTest {
        val tfc = VirtualFile(VirtualFile.Root, "../../sets/tfc.json")

        val cards = Card.readFromArray(tfc.readString())

        assertEquals(216, cards.size)
        assertTrue(tfc.exists())
    }
}