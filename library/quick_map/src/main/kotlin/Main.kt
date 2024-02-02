import eu.codlab.files.VirtualFile
import eu.codlab.lorcana.Abilities
import eu.codlab.lorcana.Franchises
import eu.codlab.lorcana.GenericCard
import eu.codlab.lorcana.Placeholders
import eu.codlab.lorcana.Set
import eu.codlab.lorcana.utils.Provider
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer

/**
 * Prepare the possible future of the predefined data
 *
 * yaml -> serializer -> library -> json
 *                               -> protobuf
 *                               -> yaml
 *                               -> etc...
 */
fun main() {
    runBlocking {
        val file = VirtualFile.Root
        val yamls = VirtualFile(file, "../../data")
        yamls.mkdirs()

        listOf(
            "abilities_yaml.yml" to Abilities.loadFromResource(),
            "franchises_yaml.yml" to Franchises.loadFromResource(),
            "placeholders_yaml.yml" to Placeholders.loadFromResource()
        ).forEach { (file, content) ->
            val vfile = VirtualFile(yamls, file)
            vfile.write(Provider.yaml.encodeToString(content).toByteArray())
        }

        val serializer =
            ListSerializer(GenericCard.serializer(String.serializer(), String.serializer()))
        listOf(
            Triple("d23_yaml.yml", Set.D23.loadFromResource(), serializer),
            Triple("rotf_yaml.yml", Set.ROTF.loadFromResource(), serializer),
            Triple("tfc_yaml.yml", Set.TFC.loadFromResource(), serializer)
        ).forEach { (file, content, serializer) ->
            val vfile = VirtualFile(yamls, file)
            vfile.write(Provider.yaml.encodeToString(serializer, content).toByteArray())
        }
    }
}
