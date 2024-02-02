package eu.codlab.lorcana.utils

import eu.codlab.http.createClient
import kotlinx.serialization.json.Json
import net.mamoe.yamlkt.Yaml
import net.mamoe.yamlkt.YamlBuilder

object Provider {
    internal val client = createClient { }

    val json = Json

    val yaml = Yaml {
        encodeDefaultValues = false
        nullSerialization = YamlBuilder.NullSerialization.NULL
    }
}
