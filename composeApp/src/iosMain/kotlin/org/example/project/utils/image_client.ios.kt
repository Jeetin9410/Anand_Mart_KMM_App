package org.example.project.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.shopping_cart
import org.jetbrains.compose.resources.painterResource

@Composable
actual fun LoadNetworkImage(url: String, modifier: Modifier) {
    val painter = asyncPainterResource(url)

    KamelImage(
        resource = painter,
        contentDescription = "Network Image"
    )
}