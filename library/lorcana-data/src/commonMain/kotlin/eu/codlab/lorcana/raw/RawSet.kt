package eu.codlab.lorcana.raw

import eu.codlab.lorcana.resources.Res
import eu.codlab.lorcana.utils.LorcanaConfiguration.github
import eu.codlab.tcgmapper.AbstractLoader
import eu.codlab.tcgmapper.Provider
import kotlinx.serialization.StringFormat
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.compose.resources.ExperimentalResourceApi

enum class RawSet(
    fileName: String
) {
    TFC("tfc"),
    ROTF("rotf"),
    ITI("iti"),
    URR("urr"),
    SSK("ssk"),
    AZU("azu"),
    ARC("arc"),
    ROJ("roj");

    @OptIn(ExperimentalResourceApi::class)
    private val loader = AbstractLoader(
        fileName,
        ListSerializer(
            RawVirtualCard.serializer()
        ),
        github,
        { Res.readBytes("files/$fileName.yml.txt") }
    )

    suspend fun loadFromGithub(tag: String = "main"): List<RawVirtualCard> =
        loader.loadFromGithub(tag)

    suspend fun loadFromResource(): List<RawVirtualCard> =
        loader.loadFromResource()

    fun to(values: List<RawVirtualCard>, encoder: StringFormat = Provider.json): String {
        return loader.to(values, encoder)
    }
}
