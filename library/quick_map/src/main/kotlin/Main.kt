import eu.codlab.files.VirtualFile
import eu.codlab.lorcana.Card
import eu.codlab.lorcana.Lorcana
import eu.codlab.lorcana.abilities.Ability
import eu.codlab.lorcana.cards.ClassificationHolder
import eu.codlab.lorcana.franchises.Franchise
import eu.codlab.lorcana.raw.SetDescription
import eu.codlab.lorcana.raw.VirtualCard
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import net.mamoe.yamlkt.Yaml
import kotlin.system.exitProcess

fun main() {
    runBlocking {
        // note : removed the logic, this is not ready yet for complete manipulation
        // and will be renamed so that it highlights the future capability of generating the
        // various assets which could be production ready in each releases :)¬

        val lorcana = Lorcana().loadFromResources()

        val json = Json {
            prettyPrint = true
        }

        val yml = Yaml {
            // nothing
        }

        val assets = VirtualFile(VirtualFile.Root, "assets")
        assets.mkdirs()

        SetDescription.entries.forEach { setDescription ->
            listOf(
                "yml" to yml,
                "json" to json
            ).forEach { (suffix, encode) ->
                val cards = lorcana.set(setDescription).cards
                val virtualCards = lorcana.set(setDescription).virtualCards

                write(assets, "${setDescription.name.lowercase()}.$suffix") {
                    encode.encodeToString(
                        ListSerializer(Card.serializer()),
                        cards
                    )
                }

                write(assets, "${setDescription.name.lowercase()}_extended.$suffix") {
                    encode.encodeToString(
                        ListSerializer(
                            VirtualCard.serializer(
                                Ability.serializer(),
                                ClassificationHolder.serializer(),
                                Franchise.serializer()
                            )
                        ),
                        virtualCards
                    )
                }
            }
        }

        exitProcess(0)
    }
}

suspend fun write(root: VirtualFile, fileName: String, encode: () -> String) {
    val file = VirtualFile(root, fileName)

    val content = encode()

    file.write(content.encodeToByteArray())
}
