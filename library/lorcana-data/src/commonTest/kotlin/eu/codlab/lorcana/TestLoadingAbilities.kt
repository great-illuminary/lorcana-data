package eu.codlab.lorcana

import eu.codlab.ignore.IgnoreAndroid
import eu.codlab.ignore.IgnoreJs
import eu.codlab.lorcana.abilities.Ability
import eu.codlab.tcgmapper.TranslationHolder
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

class TestLoadingAbilities {
    @IgnoreAndroid
    @IgnoreJs
    @Test
    fun testAbilities() = runTest {
        val content = Abilities.loadFromResource()
        assertNotNull(content)
        assertTrue(content.isNotEmpty())
        content.values.forEach { checkAbility(it) }
    }

    @IgnoreJs
    @Test
    fun testLoadingSetsFromGithub() = runTest(timeout = 20.seconds) {
        val content = Abilities.loadFromGithub()
        println("content loaded ${content.size}")
        assertNotNull(content)
        assertTrue(content.isNotEmpty())
        println("before looping...")
        content.values.forEach { checkAbility(it) }
        println("done")
    }

    private fun checkAbility(ability: Ability) = try {
        checkTranslation(ability.text)
        checkTranslation(ability.title)
    } catch (err: Throwable) {
        println("exception with $ability")
        throw err
    }

    private fun checkTranslation(translationHolder: TranslationHolder<String>?) {
        translationHolder?.let {
            // assertNotNull(it.de)
            // assertNotNull(it.fr)
            assertNotNull(it.en)
        }
    }
}
