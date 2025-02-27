import eu.codlab.lorcana.cards.InkColor
import kotlin.math.pow

fun List<InkColor>.code() = sumOf { 2.0.pow(it.ordinal.toDouble()).toInt() }