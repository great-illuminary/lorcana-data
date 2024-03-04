package eu.codlab.lorcana.raw

import eu.codlab.lorcana.cards.ClassificationHolder
import eu.codlab.lorcana.cards.Erratas
import eu.codlab.lorcana.cards.Language
import eu.codlab.lorcana.cards.to
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SetItem<C>(
    val id: Int,
    val rarity: SetItemRarity,
    val illustrator: String? = null,
    val erratas: Map<Language, Erratas<C>>? = null
)

fun SetItem<String>.to(
    mapOfClassifications: Map<String, ClassificationHolder>
): SetItem<ClassificationHolder> = SetItem(
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

enum class SetItemRarity {
    @SerialName("common")
    Common,

    @SerialName("uncommon")
    Uncommon,

    @SerialName("rare")
    Rare,

    @SerialName("super_rare")
    SuperRare,

    @SerialName("legendary")
    Legendary,

    @SerialName("enchanted")
    Enchanted,

    @SerialName("gamecon23")
    Gamecon23,

    @SerialName("d23")
    D23,

    @SerialName("d100")
    Disney100,

    @SerialName("organized_play")
    OrganizedPlay,

    @SerialName("mcm_comic_con2023")
    MCMComicCon2023,

    @SerialName("pax_unplugged2023")
    PaxUplugged2023,

    @SerialName("worlds24")
    Worlds24
}
