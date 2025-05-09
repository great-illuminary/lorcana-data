package lorcanito.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Responder {
    @SerialName("opponent")
    Opponent,

    @SerialName("self")
    Self,

    @SerialName("\$undefined")
    Undefined
}
