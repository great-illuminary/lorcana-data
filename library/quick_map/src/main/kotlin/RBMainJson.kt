import kotlinx.serialization.SerialName
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
}

@Serializable
data class RBCard(
    val name: String = "",
    val subtitle: String = "",
    @SerialName("flavor_text")
    val flavorText: String = "",
    @SerialName("card_identifier")
    val cardIdentifier: String = ""
)
