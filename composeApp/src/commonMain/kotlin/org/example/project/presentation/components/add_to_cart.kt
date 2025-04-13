package org.example.project.presentation.components

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.theme.colors.AppColors
import org.example.project.theme.typography.appTypography

@Composable
fun AddToCartButton(
    initialQuantity: Int = 0,
    onQuantityChange: (quantity: Int) -> Unit
) {
    var addedToCart by remember { mutableStateOf(initialQuantity > 0) }
    var quantity by remember { mutableStateOf(initialQuantity) }

    val transition = updateTransition(targetState = addedToCart, label = "CartTransition")

    val buttonWidth by transition.animateDp(label = "WidthAnim") { added ->
        if (added) 160.dp else 40.dp
    }

    val buttonHeight by transition.animateDp(label = "HeightAnim") { 38.dp }

    Surface(
        elevation = 0.dp,
        shape = if (addedToCart) RoundedCornerShape(12.dp) else RoundedCornerShape(24.dp),
        color = AppColors.primaryMuted,
        modifier = Modifier
            .width(buttonWidth)
            .height(buttonHeight)
            .clickable(enabled = !addedToCart) {
                quantity++
                addedToCart = true
                onQuantityChange(quantity) // Initial add
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            if (!addedToCart) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    tint = Color.White,
                    contentDescription = "Add to Cart",
                )
                quantity = 0
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = {
                            if (quantity > 1) {
                                quantity--
                                onQuantityChange(quantity)
                            } else {
                                addedToCart = false
                                onQuantityChange(0) // optional: treat as removed
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    ) {
                        Text(
                            text = "-",
                            color = Color.White,
                            style = appTypography().button.copy(fontSize = 16.sp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = quantity.toString(),
                            color = Color.White,
                            style = appTypography().button.copy(fontSize = 14.sp),
                            textAlign = TextAlign.Center
                        )
                    }

                    TextButton(
                        onClick = {
                            quantity++
                            onQuantityChange(quantity)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    ) {
                        Text(
                            text = "+",
                            color = Color.White,
                            style = appTypography().button.copy(fontSize = 16.sp)
                        )
                    }
                }
            }
        }
    }
}

