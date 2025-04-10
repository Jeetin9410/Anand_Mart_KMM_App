package org.example.project.domain.repository

import com.example.project.Skus

interface SkuRepository {
    fun saveSku(sku: Skus)
    fun getSkus(): List<Skus>
    fun getSkuById(id: String): Skus?
    fun getSkusByCategory(category: String): List<Skus>?
    fun deleteSku(id: String)
    fun deleteAllSkus()

}