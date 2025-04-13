package org.example.project.presentation.screens.main_screen.tabs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.project.CartDisplay
import com.example.project.SkuDisplay
import compose.icons.AllIcons
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.regular.CalendarMinus
import compose.icons.fontawesomeicons.regular.MinusSquare
import compose.icons.fontawesomeicons.regular.WindowMinimize
import compose.icons.fontawesomeicons.solid.Minus
import compose.icons.fontawesomeicons.solid.Plus
import org.example.project.App
import org.example.project.presentation.components.AddToCartButton
import org.example.project.presentation.components.CircleIconButton
import org.example.project.presentation.viewmodel.ProductViewModel
import org.example.project.theme.colors.AppColors
import org.example.project.theme.typography.appTypography
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.component.KoinComponent

class CartScreen : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val productViewModel = koinViewModel<ProductViewModel>()
        val products by productViewModel.displayProducts.collectAsStateWithLifecycle()
        productViewModel.getCartProducts()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Cart", style = appTypography().h6) },
                    navigationIcon = {
                        IconButton(onClick = { /* handle back */ }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Back")
                        }
                    },
                    backgroundColor = Color.White,
                    elevation = 4.dp
                )
            },
            backgroundColor = Color.White,
            bottomBar = {
                BottomBar(totalAmount = 750)
            }
        ) { padding ->
            BodyContent(Modifier.padding(padding), products.filter { it.quantityInCart > 0 })
        }

    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier, products: List<SkuDisplay>) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        AddressSection(
            name = "Faizan Khan",
            pinCode = "344022",
            address = "Opp State Bank Of India, Asotra"
        )

        Text(
            "Cart Items",
            style = appTypography().subtitle1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
        )

        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f)) {
            products.forEach { product ->
                key(product.skuId) {
                    CartItemCard(product)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OfferSection()
        Spacer(modifier = Modifier.height(12.dp))
        Spacer(modifier = Modifier.height(12.dp))

        PaymentDetailsSection(
            itemCount = 2,
            totalPrice = 750,
            discount = 0,
            deliveryCharges = "FREE"
        )
    }
}

@Composable
fun CartItemCard(product: SkuDisplay) {
    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(0.5.dp, Color.LightGray),
        modifier = Modifier.fillMaxWidth().padding(5.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NetworkImage(
                url = product.skuImage,
                modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.skuName, style = appTypography().subtitle2,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { /* delete item */ }, modifier = Modifier.size(22.dp)) {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = "Delete",
                            tint = Color.Gray
                        )
                    }
                }

                Text(
                    text = "Rs.${product.skuPrice}",
                    style = MaterialTheme.typography.body2.copy(
                        color = AppColors.textPrimary
                    ),
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 5.dp)
                )

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    QuantityChanger(quantity = product.quantityInCart.toString())
                }
            }

        }
    }
}

@Composable
fun QuantityChanger(quantity: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CircleIconButton(
            text = "-",
            contentDescription = "Decrease",
            onClick = { /* decrease */ }
        )

        Text(
            text = quantity,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        CircleIconButton(
            text = "+",
            contentDescription = "Increase",
            onClick = { /* increase */ }
        )
    }
}


@Composable
fun OfferSection() {
    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(0.5.dp, Color.LightGray),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .clickable { /* handle offer click */ }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Avail Offer (Coupon Code)",
                modifier = Modifier.weight(1f),
                style = appTypography().subtitle2
            )
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
        }
    }
}

@Composable
fun AddressSection(name: String, pinCode: String, address: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Home Address",
                color = Color.Black,
                fontSize = 12.sp,
                style = appTypography().caption
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        "$name,",
                        fontWeight = FontWeight.Bold,
                        style = appTypography().subtitle2,
                        color = Color.Gray
                    )
                    Text(
                        "$address,\n$pinCode",
                        fontWeight = FontWeight.Bold,
                        style = appTypography().caption,
                        color = Color.Gray
                    )
                }

                Text(
                    "Change",
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 8.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = appTypography().caption,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.primary
                )
            }

        }
    }
}

@Composable
fun PaymentDetailsSection(itemCount: Int, totalPrice: Int, discount: Int, deliveryCharges: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text("Payment Details", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        PaymentRow("Price ($itemCount items)", totalPrice.toString())
        PaymentRow("Discount", discount.toString())
        PaymentRow("Delivery Charges", deliveryCharges)
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        PaymentRow("Total Amount", totalPrice.toString(), isBold = true)
        Text("You saved 120 on this order", color = Color(0xFF2E7D32), fontSize = 12.sp)
    }
}

@Composable
fun PaymentRow(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Text(
            text = value,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun BottomBar(totalAmount: Int) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Rs.$totalAmount",
            color = Color.Red,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = { /* proceed action */ },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2E7D32))
        ) {
            Text("Proceed", color = Color.White)
        }
    }
}
