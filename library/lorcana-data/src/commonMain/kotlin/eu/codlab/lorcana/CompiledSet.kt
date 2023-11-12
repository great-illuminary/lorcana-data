package eu.codlab.lorcana

import eu.codlab.lorcana.abilities.Ability
import eu.codlab.lorcana.franchises.Franchise

class CompiledSet(
    private val abilities: Map<String, Ability>,
    private val franchises: Map<String, Franchise>,
    private val set: Set
) {

    private fun mapCard(card: RawCard): Card {
        return Card(
            cost = card.cost,
            inkwell = card.inkwell,
            attack = card.attack,
            defence = card.defence,
            color = card.color,
            type = card.type,
            illustrator = card.illustrator,
            number = card.number,
            rarity = card.rarity,
            languages = card.languages,
            edition = card.edition,
            actions = card.actions.mapNotNull { abilities[it] },
            setCode = card.setCode,
            franchiseId = franchises[card.franchiseId]!!,
            dummy = card.dummy,
            thirdParty = card.thirdParty,
        )
    }

    suspend fun loadFromGithub(tag: String = "main"): List<Card> {
        val cards = set.loadFromGithub("main")
        return cards.map { mapCard(it) }
    }

    suspend fun loadFromResource(): List<Card> {
        val cards = set.loadFromResource()
        return cards.map { mapCard(it) }
    }
}
