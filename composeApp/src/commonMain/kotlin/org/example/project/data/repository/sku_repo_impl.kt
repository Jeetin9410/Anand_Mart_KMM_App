package org.example.project.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.benasher44.uuid.uuid4
import com.example.project.AnandMartDb
import com.example.project.SkuDisplay
import com.example.project.Skus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import org.example.project.domain.repository.SkuRepository

class SkuRepositoryImpl(private val db: AnandMartDb) : SkuRepository {
    override fun saveSku(sku: Skus) {
        db.skusQueries.insertSku(
            id = sku.id,
            skuId = sku.skuId,
            skuName = sku.skuName,
            skuPrice = sku.skuPrice,
            skuDescription = sku.skuDescription,
            skuCategory = sku.skuCategory,
            skuImage = sku.skuImage,
            skuRatingRate = sku.skuRatingRate,
            skuRatingCount = sku.skuRatingCount,
            sessionId = sku.sessionId
        )
    }

    override fun getSkus(): List<Skus> {
        return db.skusQueries.selectAllSkus().executeAsList()
    }

    override fun getSkuById(id: String): Skus? {
        return db.skusQueries.selectSkuById(id).executeAsOneOrNull()
    }

    override fun getSkusByCategory(category: String): List<Skus> {
        return db.skusQueries.selectAllSkuCategory(category).executeAsList()
    }

    override fun deleteSku(id: String) {
        db.skusQueries.deleteSkuById(id)
    }

    override fun deleteAllSkus() {
        db.skusQueries.deleteAllSkus()
    }

    override suspend fun getDisplayProductsFlow(sessionId: String): Flow<List<SkuDisplay>> {
        return db.skusQueries
            .getAllDisplayProducts(sessionId)
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

}