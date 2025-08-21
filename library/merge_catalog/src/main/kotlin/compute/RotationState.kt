package compute

import eu.codlab.lorcana.cards.RotationState
import eu.codlab.lorcana.raw.RawVirtualCard
import eu.codlab.lorcana.raw.SetDescription

fun RawVirtualCard.computeRotation() = listOfNotNull(
    rotationStateCore(),
    rotationStateQuestPrebuiltOrInfinity()
)

private val coreValidSets = listOf(
    SetDescription.SSk,
    SetDescription.Azu,
    SetDescription.Arc,
    SetDescription.Roj,
    SetDescription.Fab
)

private fun RawVirtualCard.rotationStateCore() =
    if (variants.any { variant -> coreValidSets.any { it == variant.set } }) {
        RotationState.CoreConstructed
    } else {
        null
    }

private fun RawVirtualCard.rotationStateQuestPrebuiltOrInfinity() =
    if (variants.any { it.ravensburger.en.lowercase().contains("q") }) {
        RotationState.QuestPrebuilt
    } else {
        RotationState.InfinityConstructed
    }
