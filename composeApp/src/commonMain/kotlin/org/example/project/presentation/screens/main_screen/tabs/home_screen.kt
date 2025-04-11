package org.example.project.presentation.screens.main_screen.tabs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import compose.icons.AllIcons
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.armaani
import kotlinproject.composeapp.generated.resources.chanel
import kotlinproject.composeapp.generated.resources.lv
import kotlinproject.composeapp.generated.resources.nike
import kotlinproject.composeapp.generated.resources.rolex
import org.example.project.config.AppConfig
import org.example.project.domain.model.Product
import org.example.project.presentation.components.AddToCartButton
import org.example.project.presentation.components.AppBarRow
import org.example.project.presentation.components.BannerItem
import org.example.project.presentation.components.BrandItem
import org.example.project.presentation.components.BrandsTabs
import org.example.project.presentation.components.CategoryChipsTabs
import org.example.project.presentation.components.ParallaxCarouselBanner
import org.example.project.presentation.components.RatingBar
import org.example.project.presentation.components.SearchBar
import org.example.project.presentation.components.productItemShimmer
import org.example.project.presentation.screens.login.LoginScreen
import org.example.project.presentation.screens.product_details_screen.ProductDetails
import org.example.project.presentation.screens.product_details_screen.ProductDetailsScreen
import org.example.project.presentation.screens.product_details_screen.ProductNew
import org.example.project.presentation.viewmodel.ProductViewModel
import org.example.project.theme.colors.AppColors
import org.example.project.theme.typography.appTypography
import org.example.project.utils.shimmerEffect
import org.example.project.utils.toPriceString
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get


class HomeScreen : Screen, KoinComponent {
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val productViewModel = koinViewModel<ProductViewModel>()
        val appConfig: AppConfig = get()
        val products by productViewModel.products.collectAsState()
        var filteredProducts = products
        val isLoading by productViewModel.isLoading.collectAsState()
        var query by remember { mutableStateOf("") }
        val categories = listOf(
            "All",
            "Men's Clothing",
            "Women's Clothing",
            "Electronics",
            "Jewelery",
            "Watches"
        )
        var selectedCategory by remember { mutableStateOf(categories.first()) }
        val scrollState = rememberScrollState()
        val focusManager = LocalFocusManager.current

        Scaffold(topBar = {
            AppBarRow(subText = "A-Block, 22/D, Mayapuri, Delhi....")
        }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focusManager.clearFocus()
                        })
                    }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
                        .verticalScroll(scrollState),
                ) {
                    SearchBar(
                        query = query,
                        onQueryChange = { query = it },
                        onSearch = { }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OffersCarousel()
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Top Brands",
                            style = appTypography().subtitle1,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "See more",
                            style = appTypography().caption.copy(color = Color.DarkGray)
                        )
                    }
                    BrandsTabs(
                        categories = listOf(
                            BrandItem(1, "", Res.drawable.nike),
                            BrandItem(2, "", Res.drawable.rolex),
                            BrandItem(3, "", Res.drawable.lv),
                            BrandItem(4, "", Res.drawable.chanel),
                            BrandItem(5, "", Res.drawable.armaani)
                        ),
                        onCategorySelected = { }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Top Categories",
                            style = appTypography().subtitle1,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "See more",
                            style = appTypography().caption.copy(color = Color.DarkGray)
                        )
                    }
                    CategoryChipsTabs(
                        categories = categories,
                        selectedCategory = selectedCategory,
                        onCategorySelected = { selected ->
                            selectedCategory = selected
                            if (selected == "All") {
                                filteredProducts = products
                            } else {
                                filteredProducts = products.filter {
                                    it.category.lowercase().equals(selected.lowercase())
                                }
                            }
                        }
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (isLoading) {
                            repeat(3) { // 3 rows with 2 items each = 6 loaders
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 5.dp),
                                    horizontalArrangement = Arrangement.Center,

                                    ) {
                                    repeat(2) {
                                        productItemShimmer(
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(220.dp)
                                        )
                                    }
                                }
                            }
                        } else {
                            filteredProducts.chunked(2).forEach { rowItems ->
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    rowItems.forEach { product ->
                                        ProductItem(
                                            modifier = Modifier.weight(1f),
                                            product = product,
                                            onClick = {
                                                navigator.push(
                                                    ProductDetails(
                                                        ProductNew(
                                                            title = product.title,
                                                            priceBySize = mapOf(
                                                                "S" to product.price,
                                                                "M" to product.price.plus(10),
                                                                "L" to product.price.plus(20),
                                                                "XL" to product.price.plus(30)
                                                            ),
                                                            originalPrice = product.price.times(2),
                                                            rating = product.rating.rate,
                                                            description = product.description,
                                                            images = listOf(
                                                                product.image,
                                                                product.image,
                                                                product.image,
                                                                product.image,
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
                                                productViewModel.toggleWishlist(product.id.toLong())
                                            }
                                        )
                                    }
                                    if (rowItems.size == 1) {
                                        Spacer(Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }



                    Spacer(modifier = Modifier.height(80.dp))
                }
            }

        }

    }
}

@Composable
fun NetworkImage(url: String, modifier: Modifier, contentScale: ContentScale = ContentScale.Fit) {
    val painter = asyncPainterResource(url)
    KamelImage(
        resource = painter,
        contentDescription = "Network Image",
        modifier = modifier,
        animationSpec = tween(1000),
        onLoading = { Text("Loading...") },
        onFailure = { Text("${it.message}") },
        contentScale = contentScale

    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductItem(
    product: Product,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onWishlistClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(2.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        onClick = { onClick.invoke() }
    ) {
        Column {

            Box {
                NetworkImage(
                    product.image,
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .padding(2.dp)
                )

                IconButton(
                    onClick = { onWishlistClick() },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .background(
                            color = Color.White.copy(alpha = 0.7f),
                            shape = CircleShape
                        )
                        .size(28.dp)
                ) {
                    Icon(
                        imageVector = if (product.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Wishlist",
                        tint = if (product.isFavourite) Color.Red else Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(1.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(end = 5.dp),
                horizontalArrangement = Arrangement.End
            ) {
                product.category.let {
                    CategoryChip(category = it)
                }

            }

            Spacer(modifier = Modifier.height(1.dp))

            // Product details
            Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp)) {
                Text(
                    text = product.title,
                    style = appTypography().subtitle2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Price and Rating row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = product.price.toPriceString(),
                        style = MaterialTheme.typography.body2.copy(
                            color = AppColors.textPrimary
                        ),
                        fontWeight = FontWeight.Normal
                    )

                    RatingBar(rating = product.rating.rate)
                }

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 5.dp, start = 5.dp, end = 5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AddToCartButton()
                }


            }
        }
    }
}

@Composable
fun CategoryChip(category: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(AppColors.primaryBackground)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = category.replaceFirstChar { it.titlecase() },
            style = appTypography().overline,
            color = AppColors.primary
        )
    }
}

@Composable
fun OffersCarousel() {
    val sampleItems = listOf(
        BannerItem(
            "https://as2.ftcdn.net/jpg/03/09/86/97/1000_F_309869755_IquCHHxF7YABo2odctUGEjMrgVDSM8qV.jpg",
            "Summer Sale",
            "Up to 50% off"
        ),
        BannerItem(
            "https://as1.ftcdn.net/v2/jpg/04/65/46/52/1000_F_465465254_1pN9MGrA831idD6zIBL7q8rnZZpUCQTy.jpg",
            "New Arrivals",
            "Discover the latest trends"
        ),
        BannerItem(
            "https://as2.ftcdn.net/jpg/13/47/26/83/1000_F_1347268361_6HLdvxQSx7CtN4WLJHHOrXe9i7mCQ2dt.jpg",
            "Limited Offer",
            "Buy 1 Get 1 Free"
        ),
        BannerItem(
            "https://as2.ftcdn.net/jpg/12/84/42/03/1000_F_1284420360_QNQcVKCSWUINl8Xp4GBB1dUqgVyLQZ5h.jpg",
            "Limited Offer",
            "20% Flat Discount"
        ),
    )

    ParallaxCarouselBanner(
        items = sampleItems,
        autoScrollInterval = 3000L,
        //bannerHeight = 250,
    )
}
