package eu.codlab.lorcana

import eu.codlab.lorcana.buildconfig.BuildKonfig
import eu.codlab.lorcana.cards.CardType
import eu.codlab.lorcana.raw.VirtualCard
import eu.codlab.platform.Platform
import eu.codlab.platform.currentPlatform
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.fail

class TestCheckingCards {
    @Test
    fun testLoadingSetsFromResources() = runTest {
        if (currentPlatform != Platform.JVM) {
            return@runTest
        }

        val lorcana = Lorcana().loadFromResources()
        checkCards(lorcana.cards)
    }

    @Test
    fun testLoadingSetsFromGithub() = runTest {
        if (null != listOf(Platform.ANDROID, Platform.JS).find { currentPlatform == it }) {
            println("it's not possible to test against android or js with files at that time")
            return@runTest
        }

        listOf("main", BuildKonfig.commit).forEach {
            println("loading for $it")
            checkCards(Lorcana().loadFromGithub(it).cards)
        }
    }

    private fun checkCards(cards: List<VirtualCard>) {
        val invalidCard = cards.find {
            if (it.type == CardType.Glimmer && null != it.lore && it.lore!! > 4) {
                return@find true
            }

            if (it.type == CardType.Location && null != it.lore && it.lore!! > 2) {
                return@find true
            }

            return@find false
        }

        if (null != invalidCard) {
            when (invalidCard.type) {
                CardType.Glimmer -> fail("Lore can't be > 4")
                CardType.Location -> fail("Lore can't be > 2")
                else -> fail("the ${invalidCard.type} shouldn't have failed here")
            }
        }
    }
}
