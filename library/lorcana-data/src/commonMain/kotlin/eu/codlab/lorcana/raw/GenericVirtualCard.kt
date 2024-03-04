package eu.codlab.lorcana.raw

import eu.codlab.lorcana.abilities.Ability
import eu.codlab.lorcana.cards.CardThirdParty
import eu.codlab.lorcana.cards.CardTranslation
import eu.codlab.lorcana.cards.CardType
import eu.codlab.lorcana.cards.ClassificationHolder
import eu.codlab.lorcana.cards.InkColor
import eu.codlab.lorcana.franchises.Franchise
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenericVirtualCard<A, C, F>(
    val cost: Int = 0,
    val inkwell: Boolean = false,
    val attack: Int? = null,
    val defence: Int? = null,
    val sets: Map<SetDescription, List<SetItem<C>>> = emptyMap(),
    val color: InkColor,
    val lore: Int? = null,
    val type: CardType,
    val classifications: List<C> = emptyList(),
    val illustrator: String = "",
    val languages: Map<String, CardTranslation>,
    val actions: List<A> = emptyList(),
    @SerialName("franchise_id")
    val franchiseId: F,
    @SerialName("third_party")
    val thirdParty: CardThirdParty? = null
)

typealias RawVirtualCard = GenericVirtualCard<String, String, String>
typealias VirtualCard = GenericVirtualCard<Ability, ClassificationHolder, Franchise>

fun RawVirtualCard.to(
    abilities: Map<String, Ability>,
    mapOfClassifications: Map<String, ClassificationHolder>,
    franchises: Map<String, Franchise>
) = VirtualCard(
    cost = cost,
    inkwell = inkwell,
    attack = attack,
    defence = defence,
    sets = mutableMapOf<SetDescription, List<SetItem<ClassificationHolder>>>().let { map ->
        sets.keys.forEach {
            map[it] = sets[it]!!.map { it.to(mapOfClassifications) }
        }

        map
    },
    color = color,
    type = type,
    classifications = classifications.map { slug ->
        try {
            mapOfClassifications[slug]!!
        } catch (@Suppress("TooGenericExceptionCaught") err: Throwable) {
            println("Exception thrown because $slug couldn't be mapped")
            throw err
        }
    },
    illustrator = illustrator,
    languages = languages,
    actions = actions.mapNotNull { abilities[it] },
    franchiseId = franchises[franchiseId] ?: franchises["unknown"]!!,
    thirdParty = thirdParty
)
