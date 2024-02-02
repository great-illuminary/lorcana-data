package eu.codlab.lorcana

import eu.codlab.platform.Platform
import eu.codlab.platform.currentPlatform
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TestLoadingAbilities {

    @Test
    fun testAbilities() = runTest {
        if (null != listOf(Platform.ANDROID, Platform.JS).find { currentPlatform == it }) {
            println("this test is disabled on android or js")
            return@runTest
        }

        val content = Abilities.loadFromResource()
        assertNotNull(content)
        assertTrue(content.isNotEmpty())
    }

    @Test
    fun testLoadingSetsFromGithub() = runTest {
        val content = Abilities.loadFromGithub()
        assertNotNull(content)
        assertTrue(content.isNotEmpty())
    }
}
