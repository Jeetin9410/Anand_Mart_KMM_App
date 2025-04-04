package org.example.project.utils
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Double.toPriceString(): String {
    val rupees = this * 10
    val formatted = (rupees * 100).toInt() / 100.0
    return "₹ ${formatted.toString().let {
        if (it.contains('.')) it else "$it.00"
    }.let {
        if (it.split('.')[1].length == 1) "${it}0" else it
    }}"
}



@Composable
fun Modifier.shimmerEffect(): Modifier {
    val transition = rememberInfiniteTransition()
    val shimmerAlpha = transition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    return this.background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color.Gray.copy(alpha = shimmerAlpha.value),
                Color.LightGray.copy(alpha = shimmerAlpha.value)
            )
        )
    )
}
