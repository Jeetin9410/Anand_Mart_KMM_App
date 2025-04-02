package org.example.project.presentation.screens.main_screen.tabs

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class CartScreen : Screen {
    @Composable
    override fun Content() {
        Text("Cart Content")
    }
}