package eu.codlab.lorcana

import dev.icerock.moko.resources.FileResource
import eu.codlab.lorcana.resources.Resources
import eu.codlab.lorcana.utils.AbstractLoader
import eu.codlab.lorcana.utils.Provider
import kotlinx.serialization.StringFormat
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer

enum class Set(private val fileResource: FileResource, private val fileName: String) {
    D23(Resources.files.d23_yaml, "d23_yaml"),
    TFC(Resources.files.tfc_yaml, "tfc_yaml"),
    ROTF(Resources.files.rotf_yaml, "rotf_yaml");

    private val loader = AbstractLoader(
        fileResource,
        fileName,
        ListSerializer(RawCard.serializer(String.serializer(), String.serializer()))
    )

    suspend fun loadFromGithub(tag: String = "main"): List<RawCard> = loader.loadFromGithub(tag)

    suspend fun loadFromResource(): List<RawCard> =
        loader.loadFromResource()

    fun to(values: List<RawCard>, encoder: StringFormat = Provider.json): String {
        return loader.to(values, encoder)
    }
}
