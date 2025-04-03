package org.example.project.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.example.project.presentation.screens.main_screen.tabs.NetworkImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ParallaxCarouselBanner(
    items: List<BannerItem>,
    autoScrollInterval: Long = 5000L,
    bannerHeight: Dp = 180.dp,
    cornerRadius: Dp = 16.dp,
    modifier: Modifier = Modifier
) {
    // 1. Create state with page count
    val pageCount = items.size * 100 // Simulate infinite pages
    val pagerState = rememberPagerState(pageCount = { pageCount })

    // 2. Auto-scroll logic
    LaunchedEffect(pagerState) {
        while (true) {
            delay(autoScrollInterval)
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier
            .fillMaxWidth()
            .height(bannerHeight)
    ) { page ->
        val actualIndex = page % items.size
        val item = items[actualIndex]
        val parallaxOffset = (pagerState.currentPage - page) * 0.5f

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .clip(RoundedCornerShape(cornerRadius))
                .offset(x = parallaxOffset.dp)
        ) {
            // Image with rounded corners
            NetworkImage(
                url = item.imageRes,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            // Gradient overlay ONLY behind text
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .height(bannerHeight * 0.4f)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            startY = 0f
                        )
                    )
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.h6,
                        color = Color.White
                    )
                    item.subtitle?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body2,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            }
        }
    }
}

// Model
data class BannerItem(
    val imageRes: String, // Use "filename.png" for resources
    val title: String,
    val subtitle: String? = null
)

