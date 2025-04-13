package org.example.project.domain.repository

import com.example.project.SkuDisplay
import com.example.project.Skus
import kotlinx.coroutines.flow.Flow

interface SkuRepository {
    fun saveSku(sku: Skus)
    fun getSkus(): List<Skus>
    fun getSkuById(id: String): Skus?
    fun getSkusByCategory(category: String): List<Skus>?
    fun deleteSku(id: String)
    fun deleteAllSkus()
    suspend fun getDisplayProductsFlow(sessionId: String): Flow<List<SkuDisplay>>

}