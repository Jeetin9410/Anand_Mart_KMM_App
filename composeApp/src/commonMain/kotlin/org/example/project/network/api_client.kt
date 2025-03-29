package org.example.project.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.decodeFromJsonElement
import org.example.project.getPlatformEngine

class ApiClient {
    @OptIn(ExperimentalSerializationApi::class, )
    private val client = HttpClient(getPlatformEngine()) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                explicitNulls = false
            })
        }

        expectSuccess = false // Allows you to inspect error responses

        install(Logging) {  // ✅ Add Logging Plugin
            level = LogLevel.ALL  // Logs request & response bodies, headers, etc.
            logger = object : Logger {
                override fun log(message: String) {
                    println("API_LOG: $message") // Customize log output
                }
            }
        }

    }


    suspend fun getProducts(): List<Product> {
        val response: JsonArray = client.get("https://fakestoreapi.com/products"){
            headers {
                append("Content-Type", "application/json")
            }
        }.body()
        //val response: JsonArray = client.get("https://fakestoreapi.com/products").body()
        return Json.decodeFromJsonElement<List<Product>>(response)
    }
}





@Serializable
data class Product(
    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("price")
    val price: Double,

    @SerialName("description")
    val description: String,

    @SerialName("category")
    val category: String,

    @SerialName("image")
    val image: String,

    @SerialName("rating")
    val rating: Rating
)

@Serializable
data class Rating(
    @SerialName("rate")
    val rate: Double,

    @SerialName("count")
    val count: Int
)
