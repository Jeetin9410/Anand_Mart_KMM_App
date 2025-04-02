package org.example.project.presentation.components



import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable


@Composable
fun SideDrawer(
    drawerState: DrawerState,
    onNavigate: (String) -> Unit
) {
    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column {
                TextButton(onClick = { onNavigate("home") }) { Text("Home") }
                TextButton(onClick = { onNavigate("favorites") }) { Text("Favorites") }
                TextButton(onClick = { onNavigate("cart") }) { Text("Cart") }
                TextButton(onClick = { onNavigate("profile") }) { Text("Profile") }
            }
        }
    ) {
        // Content
    }
}
