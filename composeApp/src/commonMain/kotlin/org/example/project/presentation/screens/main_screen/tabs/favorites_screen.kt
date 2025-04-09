package org.example.project.presentation.screens.main_screen.tabs

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.project.AnandMartDb
import org.example.project.config.AppConfig
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class FavoritesScreen : Screen , KoinComponent{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val db = getKoin().get<AnandMartDb>()
        val appConfig: AppConfig = get()
        // var users by remember { mutableStateOf<List<User>>(emptyList()) }

        /*CoroutineScope(Dispatchers.Default).launch {
            users = db.userQueries.selectAllUsers()
                .executeAsList()
                .map {
                    User(
                        id = it.id,
                        username = it.username,
                        email = it.email,
                        password_hash = it.password_hash,
                        created_at = it.created_at,
                        updated_at = it.updated_at
                    )
                }
        }*/

        Text("Favorites Content")
    }
}