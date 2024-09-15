package eu.codlab.lorcana.raw

import eu.codlab.lorcana.cards.ClassificationHolder
import eu.codlab.lorcana.cards.ErratasClassification
import eu.codlab.lorcana.cards.ErratasString
import eu.codlab.lorcana.cards.Language
import eu.codlab.lorcana.cards.VariantRarity
import kotlinx.serialization.Serializable

@Serializable
data class VariantString(
    val set: SetDescription,
    val id: Int,
    val dreamborn: String,
    val rarity: VariantRarity,
    val illustrator: String? = null,
    val erratas: Map<Language, ErratasString>? = null
) {
    fun to(
        mapOfClassifications: Map<String, ClassificationHolder>
    ) = VariantClassification(
        set = set,
        id = id,
        rarity = rarity,
        illustrator = illustrator,
        dreamborn = dreamborn,
        erratas = if (null == erratas) {
            null
        } else {
            mutableMapOf<Language, ErratasClassification>().let { map ->
                erratas.keys.forEach { language ->
                    map[language] = erratas[language]!!.to(mapOfClassifications)
                }

                map
            }
        }
    )
}
