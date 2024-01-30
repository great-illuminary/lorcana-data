package eu.codlab.lorcana

import dev.icerock.moko.resources.FileResource
import eu.codlab.lorcana.resources.Resources
import eu.codlab.lorcana.utils.AbstractLoader
import eu.codlab.lorcana.utils.Provider
import kotlinx.serialization.StringFormat
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer

enum class Set(private val fileResource: FileResource, private val fileName: String) {
    D23(Resources.files.d23_json, "d23_json"),
    TFC(Resources.files.tfc_json, "tfc_json"),
    ROTF(Resources.files.rotf_json, "rotf_json");

    private val loader = AbstractLoader(
        fileResource,
        fileName,
        ListSerializer(RawCard.serializer(String.serializer(), String.serializer()))
    )

    suspend fun loadFromGithub(tag: String = "main"): List<RawCard> {
        return loader.loadFromGithub(tag)
    }

    suspend fun loadFromResource(): List<RawCard> {
        return loader.loadFromResource()
    }

    fun to(values: List<RawCard>, encoder: StringFormat = Provider.json): String {
        return loader.to(values, encoder)
    }
}
