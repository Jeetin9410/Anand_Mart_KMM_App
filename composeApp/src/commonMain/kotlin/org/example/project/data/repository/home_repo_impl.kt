package org.example.project.data.repository

import org.example.project.domain.model.Product
import org.example.project.domain.repository.HomeRepository
import org.example.project.network.ApiClient

class HomeRepositoryImpl(private val apiClient: ApiClient) : HomeRepository {
    override suspend fun getAllProducts(): List<Product> {
        return apiClient.getProducts()
    }

}