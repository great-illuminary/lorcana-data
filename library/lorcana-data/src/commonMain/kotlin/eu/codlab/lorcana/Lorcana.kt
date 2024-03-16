package eu.codlab.lorcana

import eu.codlab.lorcana.raw.RawSet
import eu.codlab.lorcana.raw.SetDescription
import eu.codlab.lorcana.raw.VirtualCard
import eu.codlab.lorcana.raw.to
import korlibs.datastructure.iterators.parallelMap

class Lorcana {

    suspend fun loadFromResources(): LorcanaLoaded {
        val abilities = Abilities.loadFromResource()
        val configuration = Configurations.loadFromResource()
        val classifications = Classifications.loadFromResource()
            .associateBy { it.slug.slug }
        val franchises = Franchises.loadFromResource()
        val rotf = RawSet.ROTF.loadFromResource()
        val tfc = RawSet.TFC.loadFromResource()
        val iti = RawSet.ITI.loadFromResource()

        val virtualCards = (tfc + rotf + iti).parallelMap {
            it.to(
                abilities,
                classifications,
                franchises
            )
        }

        val result = loadLorcana(virtualCards)
        return LorcanaLoaded(
            configuration,
            result,
            virtualCards
        )
    }

    suspend fun loadFromGithub(tag: String = "main"): LorcanaLoaded {
        val abilities = Abilities.loadFromGithub(tag)
        val configuration = Configurations.loadFromGithub(tag)
        val classifications = Classifications.loadFromGithub(tag)
            .associateBy { it.slug.slug }
        val franchises = Franchises.loadFromGithub(tag)
        val rotf = RawSet.ROTF.loadFromGithub(tag)
        val tfc = RawSet.TFC.loadFromGithub(tag)
        val iti = RawSet.ITI.loadFromGithub(tag)

        val virtualCards = (tfc + rotf + iti).parallelMap {
            it.to(
                abilities,
                classifications,
                franchises
            )
        }

        val result = loadLorcana(virtualCards)
        return LorcanaLoaded(
            configuration,
            result,
            virtualCards
        )
    }

    private fun loadLorcana(
        cards: List<VirtualCard>
    ) = SetDescription.entries.associateWith {
        loadCards(it, cards)
    }

    private fun loadCards(
        set: SetDescription,
        allVirtualCards: List<VirtualCard>
    ): Set {
        val virtualCards = allVirtualCards.filter { it.variants(set).isNotEmpty() }
        val cards = virtualCards.parallelMap { it.toCard(set) }.filterNotNull().flatten()

        return Set(
            set,
            cards = cards,
            virtualCards = virtualCards
        )
    }
}

class LorcanaLoaded(
    val configuration: Configuration,
    private val sets: Map<SetDescription, Set>,
    val cards: List<VirtualCard>
) {

    fun set(set: SetDescription) = sets[set]!!
}
