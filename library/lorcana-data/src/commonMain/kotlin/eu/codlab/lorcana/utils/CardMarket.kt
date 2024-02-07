package eu.codlab.lorcana.utils

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess

class CardMarket(val client: HttpClient) {
    suspend fun value(url: String): String {
        val content = client.get(url)

        val body = content.bodyAsText()
        if (!content.status.isSuccess()) {
            println(url)
            println(content.headers)
            println("status ${content.status} $body")
            throw CardMarketException()
        }

        // match for "labels": [...] -> managing any spaces around ": ["
        val pattern = Regex("\"labels\":(.*])")
        val matches = pattern.find(body)

        println("group matched:")
        println(matches?.groupValues)
        return matches?.groupValues?.joinToString { "," } ?: throw CardMarketException()
    }
}

class CardMarketException : Exception("Couldn't perform the operation")
