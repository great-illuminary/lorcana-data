package lorcanito

import eu.codlab.lorcana.raw.RawVirtualCard

object FallbackValues {
    fun overrides(
        card: RawVirtualCard,
        lorcanitoAbility: LorcanitoAbility
    ): Pair<String?, String?> {
        if (lorcanitoAbility.text == null) return null to null

        val found = list.find { triple ->
            null != card.variants.find { it.ravensburger.en == triple.first }
        }

        return found?.let { it.second to it.third } ?: (null to null)
    }

    val list = listOf(
        Triple("26/204 EN 6", "NEED A HAND?", "1 {I} - This character gets +1 {S} this turn."),
        Triple(
            "41/204 EN 6",
            "BEES' KNEES",
            "When you play this character, move 1 damage counter from chosen character to chosen opposing character."
        ),
        Triple(
            "55/204 EN 6",
            "OH, BAT GIZZARDS",
            "2 {I}, Choose and discard a card - Gain 1 lore."
        ),
        Triple(
            "86/204 EN 6",
            "STRONG LIKE HIS DAD",
            "3 {I} - Deal 1 damage to chosen damaged character."
        ),
        Triple(
            "88/204 EN 6",
            "YOU ARE A REALLY HOT DANCER",
            "When you play this character, chosen character gains Evasive until the start of " +
                    "your next turn. (Only characters with Evasive can challenge them.)"
        ),
        Triple(
            "14/204 EN 3",
            "BARK",
            "{E} – Chosen character gets -2 {S} until the start of your next turn."
        ),
        Triple(
            "1/204 EN 2",
            "OH, GOSH",
            "This character can't quest unless you have another Seven Dwarfs character in play."
        ),
        Triple(
            "77/204 EN 4",
            "SNEAKY IDEA",
            "When you play this character, chosen opposing character gains **Reckless** " +
                    "during their next turn. _(They can't quest and must challenge if able.)_"
        ),
        Triple(
            "7/204 EN 4",
            "INFILTRATION",
            "When you play this character, each opponent chooses and discards a card."
        )
    )
}
