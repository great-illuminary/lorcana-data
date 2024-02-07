package eu.codlab.lorcana

import eu.codlab.lorcana.abilities.Ability
import eu.codlab.lorcana.abilities.TranslationHolder
import eu.codlab.platform.Platform
import eu.codlab.platform.currentPlatform
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull
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
        content.values.forEach { checkAbility(it) }
    }

    @Test
    fun testLoadingSetsFromGithub() = runTest {
        val content = Abilities.loadFromGithub()
        assertNotNull(content)
        assertTrue(content.isNotEmpty())
        content.values.forEach { checkAbility(it) }
    }

    private fun checkAbility(ability: Ability) = try {
        checkTranslation(ability.text)
        checkTranslation(ability.title)
    } catch (err: Throwable) {
        println("exception with ${ability}")
        throw err
    }

    private fun checkTranslation(translationHolder: TranslationHolder?) {
        translationHolder?.let {
            //assertNotNull(it.de)
            //assertNotNull(it.fr)
            assertNotNull(it.en)
        }
    }
}
