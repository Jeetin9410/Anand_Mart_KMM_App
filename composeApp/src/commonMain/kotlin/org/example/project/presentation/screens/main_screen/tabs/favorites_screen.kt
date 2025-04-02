package org.example.project.presentation.screens.main_screen.tabs

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import org.koin.core.component.KoinComponent

class FavoritesScreen : Screen , KoinComponent{
    @Composable
    override fun Content() {
        Text("Favorites Content")
    }
}