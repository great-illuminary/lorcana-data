import eu.codlab.files.VirtualFile
import eu.codlab.lorcana.cards.CardTranslation
import eu.codlab.lorcana.cards.CardTranslations
import eu.codlab.lorcana.raw.Ravensburger
import eu.codlab.lorcana.raw.RawVirtualCard
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import net.mamoe.yamlkt.Yaml
import net.mamoe.yamlkt.YamlBuilder

fun main() {
    runBlocking {
        val yml = Yaml {
            // nothing
            stringSerialization = YamlBuilder.StringSerialization.DOUBLE_QUOTATION
        }
        val json = Json {
            ignoreUnknownKeys = true
        }

        val rbMap: MutableMap<String, MutableMap<String, RBCard>> = mutableMapOf()

        listOf("en", "fr", "it", "de").map { lang ->
            val file = VirtualFile(VirtualFile.Root, "data_existing/catalog/$lang/full.json")
            val content = file.readString()
            val json = json.decodeFromString<RBMainJson>(content)

            json.cards.cards.forEach { card ->
                // now make the map of id -> lang -> card
                rbMap.putIfAbsent(card.card_identifier, mutableMapOf())
                val map = rbMap[card.card_identifier]!!
                map[lang] = card
            }
        }

        println(rbMap)

        val serializer = ListSerializer(RawVirtualCard.serializer())

        listOf("tfc", "iti", "urr", "ssk", "rotf").forEach { set ->
            val file = VirtualFile(VirtualFile.Root, "data/$set.yml")
            val content = file.readString()
            val list = yml.decodeFromString(serializer, content)

            val copy = list.map { card ->
                fun copyTranslation(
                    original: CardTranslation?,
                    lang: String,
                    extractId: (Ravensburger) -> String
                ): CardTranslation? {
                    val rbcard = card.variants.map { rbMap[extractId(it.ravensburger)]?.get(lang) }
                        .find { c -> c != null }

                    val toReturn = (original ?: CardTranslation()).copy(
                        name = rbcard?.name ?: "",
                        title = rbcard?.subtitle ?: "",
                        flavour = (rbcard?.flavor_text ?: "").split("%").joinToString("\n")
                    )

                    val invalid = toReturn.name.isBlank() && toReturn.title.isNullOrBlank()
                            && toReturn.flavour.isNullOrBlank()

                    return if(!invalid) {
                        toReturn
                    } else {
                        null
                    }
                }

                val en=  copyTranslation(card.languages.en, "en") { it.en }
                if(null == en) {
                    println("having invalid info for...")
                    println(card)
                }
                card.copy(
                    languages = CardTranslations(
                        en = en!!,
                        fr = copyTranslation(card.languages.fr, "fr") { it.fr },
                        it = copyTranslation(card.languages.it, "it") { it.it },
                        de = copyTranslation(card.languages.de, "de") { it.de }
                    )
                )
            }

            val assets = VirtualFile(VirtualFile.Root, "data")

            val output = yml.encodeToString(serializer, copy)
                .split("\n")
                .filter { !it.endsWith("null") && !it.endsWith("[]") }
                .joinToString("\n")

            write(assets, "$set.yml") {
                output
            }
        }
    }
}

suspend fun write(root: VirtualFile, fileName: String, encode: () -> String) {
    val file = VirtualFile(root, fileName)

    val content = encode()

    file.write(content.encodeToByteArray())
}
