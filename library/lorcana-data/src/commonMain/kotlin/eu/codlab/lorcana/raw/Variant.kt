package eu.codlab.lorcana.raw

import eu.codlab.lorcana.cards.ClassificationHolder
import eu.codlab.lorcana.cards.Erratas
import eu.codlab.lorcana.cards.Language
import eu.codlab.lorcana.cards.VariantRarity
import eu.codlab.lorcana.cards.to
import kotlinx.serialization.Serializable

@Serializable
data class Variant<C>(
    val set: SetDescription,
    val id: Int,
    val rarity: VariantRarity,
    val illustrator: String? = null,
    val erratas: Map<Language, Erratas<C>>? = null
)

fun Variant<String>.to(
    mapOfClassifications: Map<String, ClassificationHolder>
): Variant<ClassificationHolder> = Variant(
    set = set,
    id = id,
    rarity = rarity,
    illustrator = illustrator,
    erratas = if (null == erratas) {
        null
    } else {
        mutableMapOf<Language, Erratas<ClassificationHolder>>().let { map ->
            erratas.keys.forEach { language ->
                map[language] = erratas[language]!!.to(mapOfClassifications)
            }

            map
        }
    }
)
