package eu.codlab.lorcana

import eu.codlab.lorcana.raw.SetDescription
import eu.codlab.lorcana.raw.VirtualCard
import eu.codlab.tcgmapper.Provider
import kotlinx.serialization.StringFormat
import kotlinx.serialization.builtins.ListSerializer

data class Set(
    val setDescription: SetDescription,
    val cards: List<Card>,
    val virtualCards: List<VirtualCard>
) {
    fun cardsTo(encoder: StringFormat = Provider.yaml): String {
        val serializer = Card.serializer()
        return encoder.encodeToString(ListSerializer(serializer), cards)
    }

    fun virtualCardsTo(encoder: StringFormat = Provider.yaml): String {
        val serializer = VirtualCard.serializer()

        return encoder.encodeToString(ListSerializer(serializer), virtualCards)
    }
}
