import eu.codlab.files.VirtualFile
import eu.codlab.lorcana.raw.RawVirtualCard
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.builtins.ListSerializer
import net.mamoe.yamlkt.Yaml

fun main() {
    runBlocking {
        val yml = Yaml {
            // nothing
        }

        val serializer = ListSerializer(RawVirtualCard.serializer())

        listOf("tfc", "iti", "urr", "ssk", "rotf").forEach {
            val file = VirtualFile(VirtualFile.Root, "data/$it.yml")
            val content = file.readString()
            val list = yml.decodeFromString(serializer, content)

            val assets = VirtualFile(VirtualFile.Root, "data")

            write(assets, "$it.yml") {
                yml.encodeToString(serializer, list)
            }
        }
    }
}

suspend fun write(root: VirtualFile, fileName: String, encode: () -> String) {
    val file = VirtualFile(root, fileName)

    val content = encode()

    file.write(content.encodeToByteArray())
}
