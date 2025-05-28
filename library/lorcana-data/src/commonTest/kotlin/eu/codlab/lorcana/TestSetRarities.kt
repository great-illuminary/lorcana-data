package eu.codlab.lorcana

import eu.codlab.lorcana.cards.VariantRarity
import eu.codlab.lorcana.raw.SetDescription
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TestSetRarities {
    @Test
    fun checkSet1() = runTest {
        cards(SetDescription.TFC).apply {
            assertRarity(72, VariantRarity.Common)
            assertRarity(54, VariantRarity.Uncommon)
            assertRarity(48, VariantRarity.Rare)
            assertRarity(18, VariantRarity.SuperRare)
            assertRarity(12, VariantRarity.Legendary)
            assertRarity(12, VariantRarity.Enchanted)
        }
    }

    @Test
    fun checkSet2() = runTest {
        cards(SetDescription.RotF).apply {
            assertRarity(72, VariantRarity.Common)
            assertRarity(54, VariantRarity.Uncommon)
            assertRarity(48, VariantRarity.Rare)
            assertRarity(18, VariantRarity.SuperRare)
            assertRarity(12, VariantRarity.Legendary)
            assertRarity(12, VariantRarity.Enchanted)
        }
    }

    @Test
    fun checkSet3() = runTest {
        cards(SetDescription.ItI).apply {
            assertRarity(76, VariantRarity.Common)
            assertRarity(54, VariantRarity.Uncommon)
            assertRarity(48, VariantRarity.Rare)
            assertRarity(19, VariantRarity.SuperRare)
            assertRarity(12, VariantRarity.Legendary)
            assertRarity(18, VariantRarity.Enchanted)
        }
    }

    @Test
    fun checkSet4() = runTest {
        cards(SetDescription.UrR).apply {
            assertRarity(72, VariantRarity.Common)
            assertRarity(54, VariantRarity.Uncommon)
            assertRarity(49, VariantRarity.Rare)
            assertRarity(18, VariantRarity.SuperRare)
            assertRarity(14, VariantRarity.Legendary)
            assertRarity(18, VariantRarity.Enchanted)
        }
    }

    @Test
    fun checkSet5() = runTest {
        cards(SetDescription.SSk).apply {
            assertRarity(72, VariantRarity.Common)
            assertRarity(54, VariantRarity.Uncommon)
            assertRarity(49, VariantRarity.Rare)
            assertRarity(18, VariantRarity.SuperRare)
            assertRarity(12, VariantRarity.Legendary)
            assertRarity(18, VariantRarity.Enchanted)
        }
    }

    @Test
    fun checkSet6() = runTest {
        cards(SetDescription.Azu).apply {
            assertRarity(72, VariantRarity.Common)
            assertRarity(54, VariantRarity.Uncommon)
            assertRarity(48, VariantRarity.Rare)
            assertRarity(18, VariantRarity.SuperRare)
            assertRarity(12, VariantRarity.Legendary)
            assertRarity(18, VariantRarity.Enchanted)
        }
    }

    @Test
    fun checkSet7() = runTest {
        cards(SetDescription.Arc).apply {
            assertRarity(72, VariantRarity.Common)
            assertRarity(54, VariantRarity.Uncommon)
            assertRarity(48, VariantRarity.Rare)
            assertRarity(18, VariantRarity.SuperRare)
            assertRarity(12, VariantRarity.Legendary)
            assertRarity(18, VariantRarity.Enchanted)
        }
    }

    @Test
    fun checkSet8() = runTest {
        cards(SetDescription.Roj).apply {
            assertRarity(72, VariantRarity.Common)
            assertRarity(54, VariantRarity.Uncommon)
            assertRarity(48, VariantRarity.Rare)
            assertRarity(18, VariantRarity.SuperRare)
            assertRarity(14, VariantRarity.Legendary)
            assertRarity(18, VariantRarity.Enchanted)
        }
    }

    private fun List<Card>.assertRarity(expected: Int, rarity: VariantRarity) {
        assertEquals(expected, filter { it.rarity == rarity }.size)
    }

    private suspend fun cards(set: SetDescription) = Lorcana().loadFromResources().set(set).cards
}