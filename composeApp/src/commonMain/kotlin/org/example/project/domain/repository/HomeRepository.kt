package org.example.project.domain.repository

import org.example.project.domain.model.Product

interface HomeRepository {
    suspend fun getAllProducts(): List<Product>
}