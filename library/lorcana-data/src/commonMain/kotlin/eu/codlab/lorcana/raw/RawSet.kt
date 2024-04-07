package eu.codlab.lorcana.raw

import dev.icerock.moko.resources.FileResource
import eu.codlab.lorcana.resources.Resources
import eu.codlab.lorcana.utils.LorcanaConfiguration.github
import eu.codlab.tcgmapper.AbstractLoader
import eu.codlab.tcgmapper.Provider
import kotlinx.serialization.StringFormat
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer

enum class RawSet(
    fileResource: FileResource,
    fileName: String
) {
    TFC(Resources.files.tfc, "tfc"),
    ROTF(Resources.files.rotf, "rotf"),
    ITI(Resources.files.iti, "iti"),
    URR(Resources.files.urr, "urr");

    private val loader = AbstractLoader(
        fileResource,
        fileName,
        ListSerializer(
            RawVirtualCard.serializer(
                String.serializer(),
                String.serializer(),
                String.serializer()
            )
        ),
        github
    )

    suspend fun loadFromGithub(tag: String = "main"): List<RawVirtualCard> =
        loader.loadFromGithub(tag)

    suspend fun loadFromResource(): List<RawVirtualCard> =
        loader.loadFromResource()

    fun to(values: List<RawVirtualCard>, encoder: StringFormat = Provider.json): String {
        return loader.to(values, encoder)
    }
}
