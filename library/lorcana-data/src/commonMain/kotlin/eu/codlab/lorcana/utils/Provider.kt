package eu.codlab.lorcana.utils

import eu.codlab.http.createClient
import kotlinx.serialization.json.Json

object Provider {
    val client = createClient { }

    val json = Json
}
