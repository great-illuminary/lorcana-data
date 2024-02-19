import eu.codlab.files.VirtualFile
import eu.codlab.lorcana.Card
import eu.codlab.lorcana.Lorcana
import eu.codlab.lorcana.abilities.Ability
import eu.codlab.lorcana.franchises.Franchise
import eu.codlab.lorcana.raw.SetDescription
import eu.codlab.lorcana.raw.VirtualCard
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
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

        val assets = VirtualFile(VirtualFile.Root, "assets")
        assets.mkdirs()

        listOf(
            SetDescription.Promos,
            SetDescription.TFC,
            SetDescription.RotF,
            SetDescription.ItI
        ).forEach { setDescription ->
            val cards = lorcana.set(setDescription).cards
            val virtualCards = lorcana.set(setDescription).virtualCards

            write(assets, "${setDescription.name.lowercase()}.json") {
                json.encodeToString(
                    ListSerializer(Card.serializer()),
                    cards
                )
            }

            write(assets, "${setDescription.name.lowercase()}_extended.json") {
                json.encodeToString(
                    ListSerializer(
                        VirtualCard.serializer(
                            Ability.serializer(),
                            Franchise.serializer()
                        )
                    ),
                    virtualCards
                )
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
