package org.example.project.presentation.screens.main_screen.tabs

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.project.SkuDisplay
import compose.icons.AllIcons
import compose.icons.FontAwesomeIcons
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.empty_cart
import org.example.project.domain.model.UsersModel
import org.example.project.presentation.components.CircleIconButton
import org.example.project.presentation.components.emptyStateUi
import org.example.project.presentation.viewmodel.ProductViewModel
import org.example.project.theme.colors.AppColors
import org.example.project.theme.typography.appTypography
import org.example.project.utils.toPriceString
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
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "Back",
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    },
                    backgroundColor = Color.White,
                    elevation = 4.dp
                )
            },
            backgroundColor = Color.White,
            bottomBar = {
                if (products.filter { it.quantityInCart > 0 }.isNotEmpty()) {
                    val totalPrice = products.sumOf { it.quantityInCart.toDouble() * it.skuPrice.toDouble() }
                    Column {
                        BottomBar(totalAmount = (totalPrice - 0.5 * totalPrice))
                        Spacer(modifier = Modifier.height(70.dp))
                    }
                }
            }
        ) { padding ->
            products.filter { it.quantityInCart > 0 }.let { list ->
                if (list.isEmpty()) {
                    emptyStateUi(
                        image = Res.drawable.empty_cart,
                        title = "Your cart is empty",
                        description = "Start shopping"
                    )
                } else {
                    BodyContent(
                        userDetails = productViewModel.getUserDetails(),
                        Modifier.padding(padding),
                        products.filter { it.quantityInCart > 0 },
                        onDelete = { skuId ->
                            productViewModel.addToCart(skuId, 0)
                        }
                    )

                }
            }
        }

    }
}

@Composable
fun BodyContent( userDetails: UsersModel, modifier: Modifier = Modifier, products: List<SkuDisplay> , onDelete: (skuId: Long) -> Unit) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        AddressSection(
            name = "John Doe",
            pinCode = userDetails.address.zipcode,
            address = "B-Block, H/o 24, Park Avenue\nNew Jersey"
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
                    CartItemCard(product) {
                        onDelete.invoke(it)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OfferSection()
        Spacer(modifier = Modifier.height(12.dp))
        Spacer(modifier = Modifier.height(12.dp))
        val totalPrice = products.sumOf { it.quantityInCart.toDouble() * it.skuPrice.toDouble() }
        PaymentDetailsSection(
            itemCount = products.size,
            totalPrice = totalPrice.toDouble().toPriceString(),
            discount = (0.5 * totalPrice).toDouble().toPriceString(),
            savedPrice = (0.5 * totalPrice).toDouble().toPriceString(),
            deliveryCharges = "FREE",
            totalAmount = (totalPrice - (0.5 * totalPrice)).toPriceString()
        )
    }
}

@Composable
fun CartItemCard(product: SkuDisplay, onDelete: (skuId: Long) -> Unit) {
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
                    IconButton(onClick = {
                        onDelete.invoke(product.skuId)
                    }, modifier = Modifier.size(22.dp)) {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = "Delete",
                            tint = Color.Gray
                        )
                    }
                }

                val currentPrice = product.skuPrice.toDouble() * product.quantityInCart.toDouble()
                val originalPrice =
                    2 * product.skuPrice.toDouble() * product.quantityInCart.toDouble()
                val percentage = ((originalPrice - currentPrice) / originalPrice) * 100

                Row(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "${product.skuPrice.toDouble() * product.quantityInCart.toDouble()}".toDouble()
                            .toPriceString(), style = appTypography().subtitle2,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "${2 * product.skuPrice.toDouble() * product.quantityInCart.toDouble()}".toDouble()
                            .toPriceString(),
                        style = appTypography().caption.copy(textDecoration = TextDecoration.LineThrough),
                        color = Color.Gray
                    )
                    Spacer(Modifier.weight(1f))
                    Text("⭐ ${product.skuRatingRate}", color = Color(0xFFFFC107), style = appTypography().caption)
                }

                Text(
                    text = "You saving ${percentage.toInt()}% on this",
                    style = appTypography().caption,
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 2.dp)
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Offers",
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {

                Column(
                    modifier = Modifier.wrapContentHeight().padding(vertical = 2.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home",
                        modifier = Modifier.size(16.dp)
                    )
                }

                Text(
                    "Home Address",
                    color = Color.Black,
                    modifier = Modifier.weight(1f).padding(horizontal = 2.dp),
                    style = appTypography().subtitle2
                )

                Text(
                    "Change",
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(start = 8.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = appTypography().caption,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.primary
                )
            }

            Column(
                modifier = Modifier
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

        }
    }
}

@Composable
fun PaymentDetailsSection(
    itemCount: Int,
    totalPrice: String,
    discount: String,
    savedPrice: String,
    deliveryCharges: String,
    totalAmount: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(0.5.dp, Color.LightGray), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text("Payment Details", style = appTypography().subtitle2)
        Spacer(modifier = Modifier.height(8.dp))
        PaymentRow("Price ($itemCount items)", totalPrice)
        PaymentRow("Discount", discount)
        PaymentRow("Delivery Charges", deliveryCharges)
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        PaymentRow("Total Amount", totalAmount, isBold = true)
        Text(
            "You saved $savedPrice on this order",
            color = AppColors.primary,
            fontSize = 12.sp,
            style = appTypography().caption,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PaymentRow(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = appTypography().caption)
        Text(
            text = value,
            style = appTypography().caption,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun BottomBar(totalAmount: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Column(modifier = Modifier.padding(horizontal = 5.dp)) {
            Text(
                text = "Total Amount: ",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                style = appTypography().caption
            )
            Text(
                text = totalAmount.toPriceString(),
                color = Color(0xFF2E7D32),
                fontWeight = FontWeight.Bold,
                style = appTypography().subtitle1
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { /* proceed action */ },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = AppColors.primary)
        ) {
            Text("Proceed", color = Color.White, style = appTypography().button)
        }
    }
}
