package eu.codlab.lorcana

import eu.codlab.files.VirtualFile
import korlibs.io.async.suspendTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestLoadingCards {

    @Test
    fun `test loading tfc set`() = suspendTest {
        val tfc = VirtualFile(VirtualFile.Root, "../../sets/tfc.json")

        val cards = Card.readFromArray(tfc.readString())

        assertEquals(216, cards.size)
        assertTrue(tfc.exists())
    }

    @Test
    fun `test loading d23 set`() = suspendTest {
        val d23 = VirtualFile(VirtualFile.Root, "../../sets/d23.json")

        val cards = Card.readFromArray(d23.readString())

        assertEquals(23, cards.size)
        assertTrue(d23.exists())
    }
}