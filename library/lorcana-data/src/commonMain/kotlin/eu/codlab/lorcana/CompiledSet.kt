package eu.codlab.lorcana

import eu.codlab.lorcana.raw.SetDescription
import eu.codlab.lorcana.raw.VirtualCard
import eu.codlab.lorcana.utils.Provider
import korlibs.datastructure.iterators.parallelMap
import kotlinx.serialization.StringFormat
import kotlinx.serialization.builtins.ListSerializer

class CompiledSet(
    private val cards: List<VirtualCard>,
    private val set: SetDescription
) {

    fun cards(): List<Card> {
        return cards.parallelMap { it.toCard(set) }.filterNotNull().flatten()
    }

    fun to(values: List<Card>, encoder: StringFormat = Provider.yaml): String {
        val serializer = Card.serializer()
        return encoder.encodeToString(ListSerializer(serializer), values)
    }
}
