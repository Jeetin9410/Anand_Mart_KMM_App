package org.example.project.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.google
import org.example.project.theme.colors.AppColors
import org.example.project.theme.typography.appTypography

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    imageRes: DrawableResource,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = "Profile Image",
            modifier = modifier
                .size(40.dp)
                .clip(CircleShape)
        )
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { onQueryChange(it) },
            placeholder = { Text("Search Products...", style = appTypography().caption) },
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
            textStyle = appTypography().caption,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = AppColors.primaryBackground,
                focusedBorderColor = AppColors.grey,
                unfocusedBorderColor = AppColors.grey,
                cursorColor = Color(0xFF4A5A48)
            ),
            modifier = Modifier.weight(1f).height(45.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onSearch() }
                )
            },
        )
    }
}

@Composable
fun CategoryChipsTabs(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        categories.forEach { category ->
            val isSelected = category == selectedCategory
            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) AppColors.primary else Color.LightGray.copy(alpha = 0.2f))
                    .clickable { onCategorySelected(category) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = category,
                    color = if (isSelected) Color.White else Color.Black,
                    style = appTypography().caption,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun BrandsTabs(
    categories: List<BrandItem>,
    onCategorySelected: (BrandItem) -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .padding(end = 8.dp)
    ) {
        categories.forEach { category ->
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(AppColors.accent.copy(alpha = 0.2f))
                    .clickable { onCategorySelected(category) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(category.icon),
                    contentDescription = "Brand Icon",
                    modifier = Modifier.size(50.dp)
                )
                /*Text(
                    text = category.name,
                    color = Color.Black,
                    style = appTypography().overline,
                    fontWeight = FontWeight.Normal
                )*/
            }
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}

data class BrandItem(
    val id: Int,
    val name: String,
    val icon: DrawableResource
)


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
