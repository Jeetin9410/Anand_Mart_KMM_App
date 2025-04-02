package org.example.project.presentation.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import cafe.adriel.voyager.core.screen.Screen
import org.example.project.presentation.screens.main_screen.tabs.CartScreen
import org.example.project.presentation.screens.main_screen.tabs.FavoritesScreen
import org.example.project.presentation.screens.main_screen.tabs.HomeScreen
import org.example.project.presentation.screens.main_screen.tabs.ProfileScreen

sealed class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen
) {
    object Home : BottomNavItem(
        "Home",
        Icons.Default.Home,
        HomeScreen()
    )

    object Favorites : BottomNavItem(
        "Favorites",
        Icons.Default.Favorite,
        FavoritesScreen()
    )

    object Cart : BottomNavItem(
        "Cart",
        Icons.Default.ShoppingCart,
        CartScreen()
    )

    object Profile : BottomNavItem(
        "Profile",
        Icons.Default.Person,
        ProfileScreen()
    )
}
