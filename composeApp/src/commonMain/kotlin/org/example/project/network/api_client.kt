package org.example.project.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.decodeFromJsonElement
import org.example.project.domain.model.Product
import org.example.project.domain.model.UsersModel

class ApiClient(private val httpClient: HttpClient) {

    suspend fun getProducts(): List<Product> {
        val response: JsonArray = httpClient.get("https://fakestoreapi.com/products"){
            headers {
                append("Content-Type", "application/json")
            }
        }.body()
        return Json.decodeFromJsonElement<List<Product>>(response)
    }

    suspend fun getAllUsers() : List<UsersModel> {
        val response: JsonArray = httpClient.get("https://fakestoreapi.com/users"){
            headers {
                append("Content-Type", "application/json")
            }
        }.body()
        return Json.decodeFromJsonElement<List<UsersModel>>(response)
    }
}






