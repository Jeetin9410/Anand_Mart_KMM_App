package org.example.project.presentation.screens.main_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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

        Scaffold(
            bottomBar = {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = 8.dp,
                    backgroundColor = AppColors.white
                ) {
                    BottomNavigation(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .animateContentSize(),
                        elevation = 0.dp,
                        backgroundColor = Color.Transparent
                    ) {
                        tabs.forEach { tab ->
                            BottomNavigationItem(
                                modifier = Modifier.animateContentSize(),
                                selected = selectedTab == tab,
                                onClick = {
                                    selectedTab = tab
                                    //navigator.replaceAll(tab.screen) // Replace current screen with the selected tab's screen
                                },
                                icon = {
                                    Icon(
                                        tab.icon,
                                        contentDescription = tab.title,
                                        tint = if (selectedTab == tab) AppColors.primary else AppColors.unselected
                                    )
                                },
                                label = {
                                    Text(
                                        tab.title,
                                        style = appTypography().caption,
                                        fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal,
                                        color = if (selectedTab == tab) AppColors.primary else AppColors.unselected
                                    )
                                },
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            Box {
                when (selectedTab) {
                    is BottomNavItem.Home -> HomeScreen().Content()
                    is BottomNavItem.Favorites -> FavoritesScreen().Content()
                    is BottomNavItem.Cart -> CartScreen().Content()
                    is BottomNavItem.Profile -> ProfileScreen().Content()
                }
            }
        }
    }
}


