package org.example.project.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.utils.io.core.use
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import kotlinproject.composeapp.generated.resources.shopping_cart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.example.project.network.Product
import org.example.project.presentation.viewmodel.ProductViewModel
import org.example.project.theme.typography.appTypography
import org.example.project.utils.LoadNetworkImage
import org.example.project.utils.createHttpClient
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.skia.Image // Skiko's Image class for decoding
import org.jetbrains.skia.Bitmap


class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val productViewModel = remember { ProductViewModel() }
        val products by productViewModel.products.collectAsState()
        val isLoading by productViewModel.isLoading.collectAsState()

        var showContent by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Animated Visibility for Greeting
            AnimatedVisibility(visible = showContent) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Welcome to Anand Mart!", style = appTypography().h4)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display Products
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(products) { product ->
                        ProductItem(product)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Card(
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NetworkImage("https://media.gettyimages.com/id/182176351/photo/a-picture-of-a-cat-on-a-white-background-looking-up.jpg?s=1024x1024&w=gi&k=20&c=QgL7yuINzxVyrjVMWp7MSjIgarQxB92yELqUUdFuzeo=", modifier =  Modifier.size(100.dp))

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = product.title, style = MaterialTheme.typography.h6)
                Text(text = "$${product.price}", style = MaterialTheme.typography.body1)
            }
        }
    }
}

@Composable
fun NetworkImage(url: String, modifier: Modifier) {
    val painter = asyncPainterResource(url)
    println("🚀 Loading image from URL: $url")
    println("🚀 Loading painter from URL: $painter")
    KamelImage(
        resource = painter,
        contentDescription = "Network Image",
        modifier = modifier,
        onLoading = { Text("Loading...") },  // ✅ Shows while loading
        onFailure = { Text("${it.message}") }  // ✅ Shows if image fails
    )


}