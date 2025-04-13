package org.example.project.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benasher44.uuid.uuid4
import com.example.project.Cart
import com.example.project.CartDisplay
import com.example.project.Session
import com.example.project.SkuDisplay
import com.example.project.Skus
import com.example.project.Wishlist
import com.example.project.WishlistDisplay
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import org.example.project.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.project.config.AppConfig
import org.example.project.domain.model.Product
import org.example.project.domain.model.Sku
import org.example.project.domain.repository.HomeRepository
import org.example.project.domain.repository.SessionRepository
import org.example.project.domain.repository.SkuRepository
import org.example.project.domain.repository.WishListRepository
import kotlinx.coroutines.flow.update
import org.example.project.domain.repository.CartRepository

class ProductViewModel(
    private val homeRepository: HomeRepository,
    private val skuRepository: SkuRepository,
    private val session: SessionRepository,
    private val appConfig: AppConfig,
    private val wishlistDao: WishListRepository,
    private val cartRepo: CartRepository,
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory

    private val _displayProducts = MutableStateFlow<List<SkuDisplay>>(emptyList())
    val displayProducts: StateFlow<List<SkuDisplay>> get() = _displayProducts

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _wishlistProducts = MutableStateFlow<List<WishlistDisplay>>(emptyList())
    val wishlistProducts: StateFlow<List<WishlistDisplay>> = _wishlistProducts

    private val _displayCartProducts = MutableStateFlow<List<CartDisplay>>(emptyList())
    val displayCartProducts: StateFlow<List<CartDisplay>> get() = _displayCartProducts


    init {
        fetchAndSaveProducts()
        getProductDisplay()
    }

    private fun fetchAndSaveProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                homeRepository.getAllProducts().let {
                    saveSkusToDb(it.map {
                        Skus(
                            id = uuid4().toString(),
                            skuId = it.id.toLong(),
                            skuName = it.title,
                            skuPrice = (it.price * 10).toString(),
                            skuDescription = it.description,
                            skuCategory = it.category,
                            skuImage = it.image,
                            skuRatingRate = it.rating.rate.toString(),
                            skuRatingCount = it.rating.count.toLong(),
                            sessionId = session.currentActiveSession().id
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

    fun toggleWishlist(skuId: Long) {
        viewModelScope.launch {
            val sessionId = session.currentActiveSession().id
            val currentlyInWishlist = wishlistDao.isSkuInWishlist(sessionId, skuId)
            if (currentlyInWishlist) {
                wishlistDao.deleteBySkuId(skuId)
            } else {
                wishlistDao.insert(Wishlist(sessionId = sessionId, skuId = skuId))
            }

            _displayProducts.update { currentList ->
                currentList.map {
                    if (it.skuId == skuId) it.copy(isWishlisted = if(currentlyInWishlist) 0L else 1L) else it
                }
            }
        }
    }


    fun fetchWishlistProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
               _wishlistProducts.value =  wishlistDao.getAllWishlistDisplay()
            } catch (e: Exception) {
                println("Error fetching wishlist items: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun removeSkuFromWishlist(skuId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                wishlistDao.deleteBySkuId(skuId).let {
                    _wishlistProducts.value =  wishlistDao.getAllWishlistDisplay()
                }

                _displayProducts.update { currentList ->
                    currentList.map {
                        if (it.skuId == skuId) it.copy(isWishlisted = 0L) else it
                    }
                }
            } catch (e: Exception) {
                println("Error in removing wishlist itemid : $skuId : ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addToCart(skuId: Long, quantity: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (quantity == 1) {
                    cartRepo.removeFromCart(
                        skuId = skuId.toInt(),
                        sessionId = session.currentActiveSession().id
                    )
                    cartRepo.insertIntoCart(
                        Cart(
                            sessionId = session.currentActiveSession().id,
                            skuId = skuId,
                            quantity = quantity.toLong()
                        )
                    )
                } else if (quantity == 0) {
                    cartRepo.removeFromCart(
                        skuId = skuId.toInt(),
                        sessionId = session.currentActiveSession().id
                    )
                } else {
                    cartRepo.updateCartQuantity(
                        skuId = skuId,
                        sessionId = session.currentActiveSession().id,
                        quantity = quantity
                    )
                }
                _wishlistProducts.update { currentList ->
                    currentList.map {
                        if (it.skuId == skuId) it.copy(quantity = quantity.toLong()) else it
                    }
                }
            } catch (e: Exception) {
                println("Error in adding to cart item_id : $skuId : ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getProductDisplay() {
        viewModelScope.launch {
            _selectedCategory
                .flatMapLatest { category ->
                    skuRepository.getDisplayProductsFlow(session.currentActiveSession().id)
                        .map { products ->
                            if (category == "All") products
                            else products.filter { it.skuCategory.trim().equals(category.trim(), ignoreCase = true) }
                        }
                }
                .collect { filteredProducts ->
                    _displayProducts.value = filteredProducts
                }
        }
    }

    fun setSelectedCategory(category: String) {
        _selectedCategory.value = category
    }

    fun getCartProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _displayCartProducts.value = cartRepo.selectAllCartDisplay()
            } catch (e: Exception) {

            } finally {
                _isLoading.value = false
            }
        }
    }

}