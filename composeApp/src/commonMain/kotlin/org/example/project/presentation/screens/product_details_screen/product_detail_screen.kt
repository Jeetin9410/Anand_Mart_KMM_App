package org.example.project.presentation.screens.product_details_screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import org.koin.core.component.KoinComponent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
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
import org.example.project.presentation.components.SearchBar
import org.example.project.presentation.screens.login.LoginScreen
import org.example.project.presentation.screens.main_screen.tabs.NetworkImage
import org.example.project.presentation.viewmodel.ProductViewModel
import org.example.project.theme.colors.AppColors
import org.example.project.theme.typography.appTypography
import org.example.project.utils.shimmerEffect
import org.example.project.utils.toPriceString
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.component.get

class ProductDetails(private val productSpec: ProductNew) : Screen, KoinComponent {
    @Composable
    override fun Content() {

        ProductDetailsScreen(product = productSpec, onAddToCart = {}, onBuyNow = {})
    }
}

@Composable
fun ProductDetailsScreen(
    product: ProductNew,
    onAddToCart: () -> Unit,
    onBuyNow: () -> Unit
) {
    val navigator = LocalNavigator.currentOrThrow
    val selectedSize = remember { mutableStateOf(product.sizes.first()) }
    val selectedColor = remember { mutableStateOf(product.colors.first()) }
    val expandedDescription = remember { mutableStateOf(false) }
    val currentPrice = product.priceBySize[selectedSize.value] ?: product.originalPrice

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.Transparent)
                    .zIndex(1f) // Bring above content
            ) {
                IconButton(onClick = { navigator.pop() }, modifier = Modifier.padding(8.dp)) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Black)
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onAddToCart,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = AppColors.primary)
                ) {
                    Text(
                        "Add To Cart",
                        style = appTypography().button,
                        modifier = Modifier.padding(5.dp),
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = onBuyNow,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = AppColors.primary)
                ) {
                    Text(
                        "Buy Now",
                        style = appTypography().button,
                        modifier = Modifier.padding(5.dp),
                        color = Color.White
                    )
                }
            }
        },
        backgroundColor = Color.White
    ) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {

            // Image + Thumbnails
            Box(modifier = Modifier.fillMaxWidth()) {
                NetworkImage(
                    url = (product.images.first()),
                    modifier = Modifier
                        .padding(8.dp)
                        .height(300.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                )
                LazyColumn(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .width(64.dp)
                        .height(300.dp)
                ) {
                    items(product.images) { image ->
                        NetworkImage(
                            url = image,
                            modifier = Modifier
                                .padding(4.dp)
                                .size(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(2.dp, Color.LightGray)
                        )
                    }
                }
            }

            // Title + Rating
            Text(
                product.title,
                style = appTypography().h4.copy(fontSize = 22.sp),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(currentPrice.toPriceString(), style = appTypography().body2)
                Spacer(Modifier.width(8.dp))
                Text(
                    product.originalPrice.toPriceString(),
                    style = MaterialTheme.typography.body2.copy(textDecoration = TextDecoration.LineThrough),
                    color = Color.Gray
                )
                Spacer(Modifier.weight(1f))
                Text("⭐ ${product.rating}", color = Color(0xFFFFC107))
            }

            Spacer(Modifier.height(12.dp))

            // Description
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text("Description", style = appTypography().subtitle1)
                Spacer(Modifier.height(4.dp))
                val desc = product.description
                val maxLines = if (expandedDescription.value) Int.MAX_VALUE else 3
                val showReadMore = desc.length > 120

                Text(
                    text = desc,
                    maxLines = maxLines,
                    overflow = TextOverflow.Ellipsis,
                    style = appTypography().body2,
                    textAlign = TextAlign.Justify,
                    color = Color.Gray
                )

                if (showReadMore) {
                    Text(
                        text = if (expandedDescription.value) "Read Less" else "Read More",
                        color = Color(0xFF4CAF50),
                        modifier = Modifier.clickable {
                            expandedDescription.value = !expandedDescription.value
                        }
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            // Size Selector
            Text("Select Size", Modifier.padding(start = 16.dp), style = appTypography().subtitle1)
            Spacer(Modifier.height(4.dp))
            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                product.sizes.forEach { size ->
                    OutlinedButton(
                        onClick = { selectedSize.value = size },
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = if (selectedSize.value == size) AppColors.primary else Color.LightGray.copy(alpha = 0.2f)
                        ),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(size, style = appTypography().caption, color = if (selectedSize.value == size) Color.White else Color.Black)
                    }
                }
            }
            Spacer(Modifier.height(4.dp))
            Text("Color", Modifier.padding(start = 16.dp), style = appTypography().subtitle1)
            Spacer(Modifier.height(4.dp))
            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                product.colors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(color))
                            .border(
                                width = if (selectedColor.value == color) 2.dp else 1.dp,
                                color = if (selectedColor.value == color) Color.Black.copy(alpha = 0.5f) else Color.LightGray.copy(alpha = 0.4f),
                                shape = CircleShape
                            )
                            .clickable { selectedColor.value = color }
                            .padding(4.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
            Spacer(Modifier.height(14.dp))
            // Specifications
            Text(
                "Specification",
                style = appTypography().subtitle1,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(4.dp))
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                product.specs.forEach { (label, value) ->
                    Row(Modifier.padding(vertical = 4.dp)) {
                        Text(label, modifier = Modifier.weight(1f), color = Color.Black , style = appTypography().caption)
                        Text(value, modifier = Modifier.weight(1f), color = Color.Gray,  style = appTypography().caption)
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            /*// Buttons
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                OutlinedButton(
                    onClick = onAddToCart,
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                ) {
                    Text("Add To Cart")
                }
                Button(
                    onClick = onBuyNow,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Buy Now")
                }
            }*/
        }

    }

}

data class ProductNew(
    val title: String,
    val priceBySize: Map<String, Double>,
    val originalPrice: Double,
    val rating: Double,
    val description: String,
    val images: List<String>,
    val sizes: List<String>,
    val colors: List<Long>,
    val specs: Map<String, String>
)
