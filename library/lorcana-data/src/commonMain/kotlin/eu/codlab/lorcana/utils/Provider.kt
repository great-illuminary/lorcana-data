package eu.codlab.lorcana.utils

import eu.codlab.http.createClient
import kotlinx.serialization.json.Json
import net.mamoe.yamlkt.Yaml

object Provider {
    internal val client = createClient { }

    val json = Json

    val yaml = Yaml
}
