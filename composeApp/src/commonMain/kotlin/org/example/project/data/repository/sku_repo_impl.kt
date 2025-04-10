package org.example.project.data.repository

import com.benasher44.uuid.uuid4
import com.example.project.AnandMartDb
import com.example.project.Skus
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

}