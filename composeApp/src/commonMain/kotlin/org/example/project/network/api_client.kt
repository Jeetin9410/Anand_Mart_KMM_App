package org.example.project.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.decodeFromJsonElement
import org.example.project.domain.model.Product
import org.example.project.domain.model.UsersModel
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

        engine {
            // Common configuration that works for both platforms
            pipelining = true
        }

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

    suspend fun getAllUsers() : List<UsersModel> {
        val response: JsonArray = client.get("https://fakestoreapi.com/users"){
            headers {
                append("Content-Type", "application/json")
            }
        }.body()
        //val response: JsonArray = client.get("https://fakestoreapi.com/products").body()
        return Json.decodeFromJsonElement<List<UsersModel>>(response)
    }
}






