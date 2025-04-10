package org.example.project.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benasher44.uuid.uuid4
import com.example.project.Session
import com.example.project.Skus
import io.ktor.client.HttpClient
import kotlinx.coroutines.delay
import org.example.project.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.project.config.AppConfig
import org.example.project.domain.model.Product
import org.example.project.domain.model.Sku
import org.example.project.domain.repository.HomeRepository
import org.example.project.domain.repository.SessionRepository
import org.example.project.domain.repository.SkuRepository

class ProductViewModel(
    private val homeRepository: HomeRepository,
    private val skuRepository: SkuRepository,
    private val session: SessionRepository,
    private val appConfig: AppConfig,
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                homeRepository.getAllProducts().let {
                    _products.value = it
                    saveSkusToDb(it.map {
                        Skus(
                            id = uuid4().toString(),
                            skuId = it.id.toLong(),
                            skuName = it.title,
                            skuPrice = it.price.toString(),
                            skuDescription = it.description,
                            skuCategory = it.category,
                            skuImage = it.image,
                            skuRatingRate = it.rating.rate.toString(),
                            skuRatingCount = it.rating.count.toLong(),
                            sessionId = session.currentActiveSession()?.id ?: ""
                        )
                    })
                }
            } catch (e: Exception) {
                println("Error fetching products: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun saveSkusToDb(skus: List<Skus>) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                skuRepository.deleteAllSkus()
                for (sku in skus) {
                    skuRepository.saveSku(sku)
                    delay(100)
                }
            } catch (e: Exception) {
                println("Error saving skus to db: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}