package eu.codlab.lorcana.cards

import kotlinx.serialization.Serializable

@Serializable
data class Erratas<C>(
    val classifications: List<C>? = null
)

@Suppress("TooGenericExceptionCaught")
fun Erratas<String>.to(
    mapOfClassifications: Map<String, ClassificationHolder>
): Erratas<ClassificationHolder> = Erratas(
    classifications = classifications?.map { slug ->
        try {
            mapOfClassifications[slug]!!
        } catch (err: Throwable) {
            println("Exception thrown because $slug couldn't be mapped")
            throw err
        }
    }
)
