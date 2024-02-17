package eu.codlab.lorcana

import eu.codlab.lorcana.abilities.Ability
import eu.codlab.lorcana.franchises.Franchise
import eu.codlab.lorcana.raw.RawSet
import eu.codlab.lorcana.raw.RawVirtualCard
import eu.codlab.lorcana.raw.to
import korlibs.datastructure.iterators.parallelMap

class Lorcana {
    suspend fun loadFromResources(): LorcanaLoaded {
        val abilities = Abilities.loadFromResource()
        val franchises = Franchises.loadFromResource()
        val rotf = RawSet.ROTF.loadFromResource()
        val tfc = RawSet.TFC.loadFromResource()
        val iti = RawSet.ITI.loadFromResource()

        val result = loadLorcana(abilities, franchises, tfc + rotf + iti)
        return LorcanaLoaded(result)
    }

    suspend fun loadFromGithub(tag: String = "main"): LorcanaLoaded {
        val abilities = Abilities.loadFromGithub(tag)
        val franchises = Franchises.loadFromGithub(tag)
        val rotf = RawSet.ROTF.loadFromGithub(tag)
        val tfc = RawSet.TFC.loadFromGithub(tag)
        val iti = RawSet.ITI.loadFromGithub(tag)

        val result = loadLorcana(abilities, franchises, tfc + rotf + iti)
        return LorcanaLoaded(result)
    }

    private suspend fun loadLorcana(
        abilities: Map<String, Ability>,
        franchises: Map<String, Franchise>,
        cards: List<RawVirtualCard>
    ): Map<SetDescription, Set> = listOf(
        SetDescription.TFC,
        SetDescription.RotF,
        SetDescription.Promos,
        SetDescription.ItI
    ).map {
        it to loadCards(it, abilities, franchises, cards)
    }.toMap()

    private fun loadCards(
        set: SetDescription,
        abilities: Map<String, Ability>,
        franchises: Map<String, Franchise>,
        rawVirtualCards: List<RawVirtualCard>
    ): Set {
        val virtualCards = rawVirtualCards.filter { null != it.sets[set] }
            .parallelMap { it.to(abilities, franchises) }
        val cards = virtualCards.parallelMap { it.toCard(set) }.filterNotNull().flatten()

        return Set(
            set,
            cards = cards,
            virtualCards = virtualCards
        )
    }
}

class LorcanaLoaded(
    private val sets: Map<SetDescription, Set>
) {

    fun set(set: SetDescription) = sets[set]
}
