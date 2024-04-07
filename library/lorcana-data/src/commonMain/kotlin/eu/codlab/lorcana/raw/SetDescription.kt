package eu.codlab.lorcana.raw

import kotlinx.serialization.SerialName

enum class SetDescription {
    @SerialName("tfc")
    TFC,

    @SerialName("rotf")
    RotF,

    @SerialName("promos")
    Promos,

    @SerialName("iti")
    ItI,

    @SerialName("urr")
    UrR
}
