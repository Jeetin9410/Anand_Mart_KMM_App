package org.example.project.presentation.screens.main_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.presentation.components.BottomNavItem
import org.example.project.presentation.screens.main_screen.tabs.CartScreen
import org.example.project.presentation.screens.main_screen.tabs.FavoritesScreen
import org.example.project.presentation.screens.main_screen.tabs.HomeScreen
import org.example.project.presentation.screens.main_screen.tabs.ProfileScreen
import org.example.project.theme.colors.AppColors
import org.example.project.theme.typography.appTypography
import org.koin.core.component.KoinComponent


class MainScreen : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val tabs = listOf(
            BottomNavItem.Home,
            BottomNavItem.Favorites,
            BottomNavItem.Cart,
            BottomNavItem.Profile
        )

        var selectedTab by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Home) }
        val focusRequester = remember { FocusRequester() }
        var isFocused by remember { mutableStateOf(false) }

        Scaffold(
            bottomBar = {
                val backgroundColor by animateColorAsState(
                    targetValue = AppColors.white,
                    animationSpec = tween(300)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(color = backgroundColor)
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                        .focusRequester(focusRequester)
                        .focusable()
                        .onFocusChanged { isFocused = it.isFocused },
                    shape = RoundedCornerShape(16.dp),
                    elevation = animateDpAsState(
                        targetValue = if (isFocused) 12.dp else 8.dp,
                        animationSpec = tween(300)
                    ).value,
                    backgroundColor = backgroundColor
                ) {
                    BottomNavigation(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .animateContentSize(),
                        elevation = 0.dp,
                        backgroundColor = Color.Transparent
                    ) {
                        tabs.forEach { tab ->

                            val selected = selectedTab == tab
                            val selectionState = remember { mutableStateOf(false) }

                            BottomNavigationItem(
                                modifier = Modifier.animateContentSize(),
                                selected = selected,
                                onClick = {
                                    selectionState.value = true
                                    selectedTab = tab
                                    //navigator.replaceAll(tab.screen) // Replace current screen with the selected tab's screen
                                },
                                icon = {
                                    val scale by animateFloatAsState(
                                        targetValue = if (selectionState.value && selected) 1.2f else 1f,
                                        animationSpec = spring(dampingRatio = 0.4f)
                                    )

                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            tab.icon,
                                            contentDescription = tab.title,
                                            modifier = Modifier.scale(scale),
                                            tint = if (selected) AppColors.primary else AppColors.unselected
                                        )

                                        AnimatedVisibility(
                                            visible = selected,
                                            enter = fadeIn() + expandHorizontally(),
                                            exit = fadeOut() + shrinkHorizontally()
                                        ) {
                                            Box(
                                                Modifier
                                                    .width(24.dp)
                                                    .background(AppColors.primary)
                                            )
                                        }
                                    }
                                },
                                label = {
                                    Text(
                                        tab.title,
                                        style = appTypography().caption.copy(
                                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                            fontSize = if (selected) 13.sp else 12.sp
                                        ),
                                        color = if (selected) AppColors.primary else AppColors.unselected,
                                        modifier = Modifier
                                    )
                                },
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            Box {
                Crossfade(
                    targetState = selectedTab,
                    animationSpec = tween(300)
                ) { currentTab ->
                    when (currentTab) {
                        is BottomNavItem.Home -> HomeScreen().Content()
                        is BottomNavItem.Favorites -> FavoritesScreen().Content()
                        is BottomNavItem.Cart -> CartScreen().Content()
                        is BottomNavItem.Profile -> ProfileScreen().Content()
                    }
                }
                /*when (selectedTab) {
                    is BottomNavItem.Home -> HomeScreen().Content()
                    is BottomNavItem.Favorites -> FavoritesScreen().Content()
                    is BottomNavItem.Cart -> CartScreen().Content()
                    is BottomNavItem.Profile -> ProfileScreen().Content()
                }*/
            }
        }
    }
}


