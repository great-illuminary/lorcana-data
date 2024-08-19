package eu.codlab.lorcana

import eu.codlab.ignore.IgnoreAndroid
import eu.codlab.ignore.IgnoreJs
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

class TestLoadingFranchises {
    @IgnoreAndroid
    @IgnoreJs
    @Test
    fun testFranchises() = runTest {
        val content = Franchises.loadFromResource()
        assertNotNull(content)
        assertTrue(content.isNotEmpty())
    }

    @IgnoreJs
    @Test
    fun testLoadingFranchisesFromGithub() = runTest(timeout = 20.seconds) {
        val content = Franchises.loadFromGithub()
        assertNotNull(content)
        assertTrue(content.isNotEmpty())
    }
}
