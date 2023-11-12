package eu.codlab.lorcana

import eu.codlab.platform.Platform
import eu.codlab.platform.currentPlatform
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TestLoadingFranchises {

    @Test
    fun testFranchises() = runTest {
        if (null != listOf(Platform.ANDROID, Platform.JS).find { currentPlatform == it }) {
            println("this test is disabled on android or js")
            return@runTest
        }

        val content = Franchises.loadFromResource()
        assertNotNull(content)
        assertTrue(content.isNotEmpty())
    }

    @Test
    fun testLoadingFranchisesFromGithub() = runTest {
        val content = Franchises.loadFromGithub()
        assertNotNull(content)
        assertTrue(content.isNotEmpty())
    }
}
