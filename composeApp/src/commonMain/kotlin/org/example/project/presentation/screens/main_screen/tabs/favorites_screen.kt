package org.example.project.presentation.screens.main_screen.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.project.AnandMartDb
import org.example.project.config.AppConfig
import org.example.project.domain.model.Product
import org.example.project.domain.model.Rating
import org.example.project.presentation.components.CategoryChipsTabs
import org.example.project.presentation.components.productItemShimmer
import org.example.project.presentation.screens.product_details_screen.ProductDetails
import org.example.project.presentation.screens.product_details_screen.ProductNew
import org.example.project.presentation.screens.profile_screen.ProfileEditScreen
import org.example.project.presentation.screens.profile_screen.ProfileScreenUi
import org.example.project.presentation.viewmodel.ProductViewModel
import org.example.project.theme.typography.appTypography
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class FavoritesScreen : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val db = getKoin().get<AnandMartDb>()
        val appConfig: AppConfig = get()
        val productViewModel = koinViewModel<ProductViewModel>()
        val products by productViewModel.wishlistProducts.collectAsState()
        val isLoading by productViewModel.isLoading.collectAsState()
        var filteredProducts = products
        val categories = listOf(
            "All",
            "Men's Clothing",
            "Women's Clothing",
            "Electronics",
            "Jewelery",
            "Watches"
        )
        var selectedCategory by remember { mutableStateOf(categories.first()) }

        productViewModel.fetchWishlistProducts()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Wishlist", style = appTypography().h6, color = Color.Black) },
                    backgroundColor = Color.White,
                    elevation = 2.dp,
                    navigationIcon = {
                    }
                )
            },
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
                Text(
                    "Filters",
                    style = appTypography().subtitle1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                )
                CategoryChipsTabs(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selected ->
                        selectedCategory = selected
                        if (selected == "All") {
                            filteredProducts = products
                        } else {
                            filteredProducts = products.filter {
                                it.skuCategory.lowercase().equals(selected.lowercase())
                            }
                        }
                    }
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(2.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    if (isLoading) {
                        items(6) { productItemShimmer() }
                    } else {
                        items(filteredProducts) { product ->
                            ProductItem(
                                product = Product(
                                    id = product.skuId.toInt(),
                                    title = product.skuName,
                                    price = product.skuPrice.toDouble(),
                                    description = product.skuDescription,
                                    category = product.skuCategory,
                                    image = product.skuImage,
                                    rating = Rating(
                                        rate = product.skuRatingRate.toDouble(),
                                        count = product.skuRatingCount.toInt()
                                    ),
                                    isFavourite = true,
                                    quantity = product.quantity?.toInt() ?: 0
                                ),
                                modifier = Modifier.padding(4.dp),
                                onClick = {
                                    navigator.push(
                                        ProductDetails(
                                            ProductNew(
                                                title = product.skuName,
                                                priceBySize = mapOf(
                                                    "S" to product.skuPrice.toDouble(),
                                                    "M" to product.skuPrice.toDouble().plus(10),
                                                    "L" to product.skuPrice.toDouble().plus(20),
                                                    "XL" to product.skuPrice.toDouble().plus(30)
                                                ),
                                                originalPrice = product.skuPrice.toDouble().times(2),
                                                rating = product.skuRatingRate.toDouble(),
                                                description = product.skuDescription,
                                                images = listOf(
                                                    product.skuImage,
                                                    product.skuImage,
                                                    product.skuImage,
                                                    product.skuImage,
                                                ),
                                                sizes = listOf("S", "M", "L", "XL"),
                                                colors = listOf(
                                                    0xFF80DEEA,
                                                    0xFFCFD8DC,
                                                    0xFFD32F2F,
                                                    0xFFF8BBD0
                                                ),
                                                specs = mapOf(
                                                    "Closure" to "Button",
                                                    "Collar" to "Notched Lapel",
                                                    "Fabric" to "Linen",
                                                    "Length" to "Longline",
                                                    "Lining Fabric" to "Unlined"
                                                )
                                            )
                                        )
                                    )
                                },
                                onWishlistClick = {
                                    productViewModel.removeSkuFromWishlist(product.skuId)
                                },
                                onAddToCartClick = { quantity ->
                                    productViewModel.addToCart(product.skuId.toLong(), quantity)

                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

    }
}