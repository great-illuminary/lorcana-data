import kotlinx.serialization.Serializable

@Serializable
data class RBMainJson(
    val cards: RBCards
)

@Serializable
data class RBCards(
    val characters: List<RBCard>,
    val locations: List<RBCard>,
    val items: List<RBCard>,
    val actions: List<RBCard>
) {
    val cards = characters + locations + items + actions

    fun find(identifier: String): RBCard? {
        listOf(characters, locations, items, actions).forEach { list ->
            val found = list.find { it.card_identifier == identifier }
            if (null != found) return found
        }

        return null
    }
}

@Serializable
data class RBCard(
    val name: String = "",
    val subtitle: String = "",
    val flavor_text: String = "",
    val card_identifier: String = ""
)
