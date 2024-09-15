package eu.codlab.lorcana.cards

import kotlinx.serialization.Serializable

@Serializable
data class ErratasString(
    val classifications: List<String>? = null
) {
    @Suppress("TooGenericExceptionCaught")
    fun to(mapOfClassifications: Map<String, ClassificationHolder>) = ErratasClassification(
        classifications = classifications?.map { slug ->
            try {
                mapOfClassifications[slug]!!
            } catch (err: Throwable) {
                println("Exception thrown because $slug couldn't be mapped")
                throw err
            }
        }
    )
}
