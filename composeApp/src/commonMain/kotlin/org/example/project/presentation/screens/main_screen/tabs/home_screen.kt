package org.example.project.presentation.screens.main_screen.tabs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.example.project.config.AppConfig
import org.example.project.domain.model.Product
import org.example.project.presentation.components.AppBarRow
import org.example.project.presentation.components.BannerItem
import org.example.project.presentation.components.ParallaxCarouselBanner
import org.example.project.presentation.screens.login.LoginScreen
import org.example.project.presentation.viewmodel.ProductViewModel
import org.example.project.theme.colors.AppColors
import org.example.project.theme.typography.appTypography
import org.example.project.utils.toPriceString
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get


class HomeScreen : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val productViewModel = koinViewModel<ProductViewModel>()
        val appConfig: AppConfig = get()
        val products by productViewModel.products.collectAsState()
        val isLoading by productViewModel.isLoading.collectAsState()

        var showContent by remember { mutableStateOf(true) }

        Scaffold(topBar = {
            AppBarRow(subText = "A-Block, 22/D, Mayapuri, Delhi....")
        }) {
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Hi Jeetin,",
                    style = appTypography().h6,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp)
                )
                Text(
                    text = "Welcome to Anand Mart",
                    style = appTypography().body2,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                MovieCarousel()
                Spacer(modifier = Modifier.height(16.dp))

                // Display Products
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(2.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        items(products) { product ->
                            ProductItem(
                                product = product,
                                modifier = Modifier.padding(4.dp),
                                onClick = { }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
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

@Composable
fun ProductItem(
    product: Product,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp
    ) {
        Column {

            NetworkImage(
                product.image, modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .aspectRatio(1f)
            )
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
                    style = appTypography().body1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
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
                        style = MaterialTheme.typography.caption.copy(
                            color = AppColors.textPrimary
                        ),
                        fontWeight = FontWeight.Bold
                    )

                    RatingBar(rating = product.rating.rate)
                }
            }
        }
    }
}

@Composable
fun RatingBar(
    rating: Double,
    maxStars: Int = 5
) {
    Row {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = AppColors.secondary,
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = rating.toString(),
            style = appTypography().body2.copy(fontSize = 12.sp),
            color = AppColors.textSecondary
        )
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
fun MovieCarousel() {
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
            "Limited Offer"
        ),
        BannerItem(
            "https://as2.ftcdn.net/jpg/12/84/42/03/1000_F_1284420360_QNQcVKCSWUINl8Xp4GBB1dUqgVyLQZ5h.jpg",
            "Limited Offer"
        ),
    )

    ParallaxCarouselBanner(
        items = sampleItems,
        autoScrollInterval = 3000L,
        //bannerHeight = 250,
    ) /*{ clickedItem ->
        // Handle click
    }*/
}