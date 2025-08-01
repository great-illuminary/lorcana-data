import eu.codlab.files.VirtualFile
import eu.codlab.http.createClient
import eu.codlab.lorcana.Lorcana
import eu.codlab.lorcana.abilities.Ability
import eu.codlab.lorcana.abilities.AbilityType
import eu.codlab.lorcana.cards.CardTranslation
import eu.codlab.lorcana.cards.CardTranslations
import eu.codlab.lorcana.cards.CardType
import eu.codlab.lorcana.cards.VariantRarity
import eu.codlab.lorcana.raw.Ravensburger
import eu.codlab.lorcana.raw.RawVirtualCard
import eu.codlab.lorcana.raw.SetDescription
import eu.codlab.lorcana.raw.VariantClassification
import eu.codlab.lorcana.raw.VariantString
import eu.codlab.tcgmapper.TranslationHolder
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.util.cio.writeChannel
import io.ktor.utils.io.copyAndClose
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import lorcanito.FallbackValues
import lorcanito.LoadLorcanito
import net.mamoe.yamlkt.Yaml
import net.mamoe.yamlkt.YamlBuilder
import official.LoadOfficialData
import java.io.File
import kotlin.system.exitProcess

val serializer = ListSerializer(RawVirtualCard.serializer())
val yml = Yaml {
    // nothing
    stringSerialization = YamlBuilder.StringSerialization.DOUBLE_QUOTATION
}
val json = Json {
    ignoreUnknownKeys = true
}

/**
 * This script is a WIP and uncleaned version which will manage the raw files and create the appropriate
 * merge with the existing yaml files.
 *
 * One thing to clean is the above assumption that the yaml is ready
 * Then clean everything up
 * And remove the various "if (static) then" -> to make sure we have nothing which is set specific
 * or if those exists, they can be described properly and not in code
 */
fun main() {
    runBlocking {
        load()

        exitProcess(0)
    }
}

@Suppress(
    "LongMethod",
    "ComplexMethod",
    "TooGenericExceptionCaught",
    "SwallowedException",
    "MagicNumber"
)
private suspend fun load() {
    val client = createClient { }
    val rootProject = VirtualFile(VirtualFile.Root, "../../")

    // preparing the empty files for some sets
    prepareEmpty(rootProject, "azu", 6, SetDescription.Azu, "006")
    prepareEmpty(rootProject, "arc", 7, SetDescription.Arc, "007")
    prepareEmpty(rootProject, "roj", 8, SetDescription.Roj, "008")

    val official = runBlocking {
        LoadOfficialData(rootProject).also { it.loadLanguages() }
    }

    val lorcanito = runBlocking { LoadLorcanito.load() }

    val rbMap: MutableMap<String, MutableMap<String, RBCard>> = mutableMapOf()
    val rbHighRes: MutableMap<String, String> = mutableMapOf()

    var rbCards: RBCards? = null

    listOf("en", "fr", "it", "de", "zh", "ja").map { lang ->
        val file = VirtualFile(rootProject, "data_existing/catalog/$lang/full.json")
        val content = file.readString()
        val json = json.decodeFromString<RBMainJson>(content)
        if (rbCards == null) rbCards = json.cards

        json.cards.cards.forEach { card ->
            // now make the map of id -> lang -> card
            rbMap.putIfAbsent(card.cardIdentifier, mutableMapOf())
            val map = rbMap[card.cardIdentifier]!!
            map[lang] = card
            card.highResImage?.let { rbHighRes[card.cardIdentifier] = it.url }
        }
    }

    if (rbCards == null) throw IllegalStateException("rbCards can't be null at this point")

    listOf("tfc", "iti", "urr", "ssk", "rotf", "azu", "arc", "roj", "fab", "whi").forEach { set ->
        val file = VirtualFile(rootProject.absolutePath, "data/$set.yml")
        val content = file.readString()
        val list = yml.decodeFromString(serializer, content)

        val copy = list.map { card ->
            val lorcanitoCard = card.variants.firstNotNullOfOrNull {
                lorcanito.card(it.set, it.id)
            }

            card.variants.forEach { variant ->
                val rb = variant.ravensburger

                listOf(
                    Triple("it", rb.it, "webp"),
                    Triple("fr", rb.fr, "webp"),
                    Triple("en", rb.en, "webp"),
                    Triple("de", rb.de, "webp"),
                    Triple("zh", rb.zh, "png"),
                    Triple("ja", rb.ja, "png")
                ).forEach { (lang, holder, extension) ->
                    val folderName = "images/${variant.set.name.lowercase()}/$lang"
                    val folder = VirtualFile(rootProject, folderName)
                    folder.mkdirs()

                    val fileName = "${variant.id}${variant.suffix ?: ""}.$extension"
                    val file = VirtualFile(folder, fileName)

                    var url: String? = null
                    try {
                        if (!file.exists()) {
                            val nativeFile = File(file.absolutePath)
                            rbHighRes[holder]?.replace(
                                "yantootech.com",
                                "ravensburgerbackend.cn"
                            )?.let { rbUrl ->
                                url = rbUrl
                                client.get(rbUrl).bodyAsChannel()
                                    .copyAndClose(nativeFile.writeChannel())
                            }
                            println("finished downloading $fileName")
                        }
                    } catch (err: Throwable) {
                        println("couldn't download $folderName/$fileName from $holder -> $url")
                        err.printStackTrace()
                    }
                }
            }

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
                    flavour = (rbcard?.flavorText ?: "").split("%").joinToString("\n")
                )

                val invalid = toReturn.name.isBlank() &&
                        toReturn.title.isNullOrBlank() &&
                        toReturn.flavour.isNullOrBlank()

                return if (!invalid) {
                    toReturn
                } else {
                    null
                }
            }

            val en = copyTranslation(card.languages.en, "en") { it.en }
            if (null == en) {
                println("having invalid info for...")
                println(card)
            }

            val expectedKey = card.variants.first().ravensburger.en
            val holder = rbMap[expectedKey]

            if (null == holder) {
                println("Warning !! card is not known -> $expectedKey for $set")
                return@map card
            }

            val ravensBurgerCard = holder["en"]!!

            val fallbackTexts = listOf(CardType.Action, CardType.Item)

            // now check the actions which need to fallback to something
            val (defaultActionTitle, defaultActionText) = if (fallbackTexts.contains(card.type)) {
                lorcanitoCard?.name to lorcanitoCard?.text
            } else {
                "" to ""
            }

            card.copy(
                cost = ravensBurgerCard.inkCost,
                inkwell = ravensBurgerCard.inkConvertible,
                lore = ravensBurgerCard.questValue,
                attack = ravensBurgerCard.strength,
                defence = ravensBurgerCard.willpower,
                moveCost = ravensBurgerCard.moveCost,
                illustrator = ravensBurgerCard.author,
                type = rbCards!!.type(ravensBurgerCard),
                classifications = ravensBurgerCard.subtypes,
                abilities = lorcanitoCard?.actualAbilities()?.map {
                    val (overrideTitle, overrideText) = FallbackValues.overrides(card, it)

                    Ability(
                        type = it.type ?: AbilityType.Undefined,
                        ability = it.ability,
                        title = TranslationHolder(
                            en = overrideTitle ?: it.name ?: it.ability ?: defaultActionTitle ?: ""
                        ),
                        text = TranslationHolder(
                            en = overrideText ?: it.text ?: defaultActionText ?: ""
                        ),
                    )
                } ?: emptyList(),
                languages = CardTranslations(
                    en = en!!,
                    fr = copyTranslation(card.languages.fr, "fr") { it.fr },
                    it = copyTranslation(card.languages.it, "it") { it.it },
                    de = copyTranslation(card.languages.de, "de") { it.de },
                    zh = copyTranslation(card.languages.zh, "zh") { it.zh ?: "" },
                    ja = copyTranslation(card.languages.ja, "ja") { it.ja ?: "" }
                ),
                // update the colors if required
                colors = if (card.colors.code() != ravensBurgerCard.colors.code()) {
                    ravensBurgerCard.colors
                } else {
                    card.colors
                },
                variants = card.variants.map { variant ->
                    val subRavensBurgerCard = rbMap[variant.ravensburger.en]?.let { it["en"] }
                        ?: return@map variant // in the case of an unreleased card

                    variant.copy(
                        illustrator = subRavensBurgerCard.author,
                        ravensburger = variant.ravensburger.copy(
                            cultureInvariantId = subRavensBurgerCard.cultureInvariantId
                                ?: variant.ravensburger.cultureInvariantId,
                            sortNumber = subRavensBurgerCard.sortNumber
                                ?: variant.ravensburger.sortNumber,
                            zh = (variant.ravensburger.zh ?: "").ifBlank {
                                // for now, since the sets are not matching with worldwide...
                                if (null != listOf(
                                        " EN 1",
                                        " EN 2",
                                        " EN 3",
                                        " EN 4"
                                    ).find { suffix -> variant.ravensburger.en.contains(suffix) }
                                ) {
                                    variant.ravensburger.en.replace(" EN ", " ZH ")
                                } else {
                                    null
                                }
                            },
                            ja = (variant.ravensburger.ja ?: "").ifBlank {
                                // for now, since the sets are not matching with worldwide...
                                if (null != listOf(
                                        " EN 1",
                                        " EN 2",
                                        " EN 3",
                                        " EN 4"
                                    ).find { suffix -> variant.ravensburger.en.contains(suffix) }
                                ) {
                                    variant.ravensburger.en.replace(" EN ", " JA ")
                                } else {
                                    null
                                }
                            }
                        ),
                        rarity = subRavensBurgerCard.actualRarity ?: variant.rarity
                    )
                }
            )
        }

        val main = VirtualFile(VirtualFile.Root, "../..")
        val assets = VirtualFile(main, "data")
        if (!assets.exists()) assets.mkdirs()

        val output = yml.encodeToString(serializer, copy)
            .split("\n")
            .filter { !it.endsWith("null") && !it.endsWith("[]") }
            .joinToString("\n")

        write(assets, "$set.yml") {
            output
        }
    }

    // now we'll check if we have mapped all cards...
    val lorcana = Lorcana()
    val loaded = lorcana.loadFromResources()

    listOf("en", "fr", "it", "de", "zh", "ja").map { lang ->
        val file = VirtualFile(rootProject, "data_existing/catalog/$lang/full.json")
        val content = file.readString()
        val json = json.decodeFromString<RBMainJson>(content)

        json.cards.cards.forEach { rbCard ->
            val foundCard = loaded.cards.find {
                null != it.variants.find { variant -> variant.match(rbCard) }
            }

            if (null != foundCard) return@forEach // skip the next process

            println("card ${rbCard.cardIdentifier} wasn't found")
        }
    }
}

fun VariantClassification.match(rbCard: RBCard) = listOf(
    this.ravensburger.en,
    this.ravensburger.ja,
    this.ravensburger.zh,
    this.ravensburger.it,
    this.ravensburger.de,
    this.ravensburger.fr
).contains(rbCard.cardIdentifier)

suspend fun write(root: VirtualFile, fileName: String, encode: () -> String) {
    val file = VirtualFile(root, fileName)

    val content = encode()

    if (!file.exists()) file.touch()

    file.write(content.encodeToByteArray())
}

suspend fun prepareEmpty(
    rootProject: VirtualFile,
    set: String,
    setId: Int,
    setDescription: SetDescription,
    dreamborn: String
) {
    val file = VirtualFile(rootProject.absolutePath, "data/$set.yml")
    println("having file -> ${file.absolutePath}")
    if (file.exists()) {
        println("skipping setting $set")
        return
    }

    val list = mutableListOf<RawVirtualCard>()
    @Suppress("ForEachOnRange", "MagicNumber")
    (1..204).forEach { index ->
        list.add(
            RawVirtualCard(
                variants = listOf(
                    VariantString(
                        set = setDescription,
                        id = index,
                        dreamborn = "$dreamborn-${index.toString().padStart(3, '0')}",
                        ravensburger = Ravensburger(
                            en = "$index/204 EN $setId",
                            fr = "$index/204 FR $setId",
                            it = "$index/204 IT $setId",
                            de = "$index/204 DE $setId",
                            zh = "$index/204 ZH $setId",
                            ja = "$index/204 JA $setId",
                            cultureInvariantId = -1,
                            sortNumber = -1
                        ),
                        rarity = VariantRarity.Common
                    )
                ),
                type = CardType.Item,
                languages = CardTranslations(
                    en = CardTranslation(
                        name = ""
                    )
                ),
                franchiseId = ""
            )
        )
    }

    val assets = VirtualFile(rootProject, "data")
    if (!assets.exists()) assets.mkdirs()

    val output = yml.encodeToString(serializer, list)
        .split("\n")
        .filter { !it.endsWith("null") && !it.endsWith("[]") }
        .joinToString("\n")

    println("writing to $set.yml")
    write(assets, "$set.yml") {
        output
    }
}
