package org.example.project.presentation.screens.main_screen.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.realm.kotlin.ext.query
import org.example.project.data.database.AppDatabase.realm
import org.example.project.data.db_models.User
import org.example.project.theme.typography.appTypography

class ProfileScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val users = realm.query<User>().find()

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            users.forEach {
                Text("User ID: ${it.id} Name: ${it.name}" ,style = appTypography().body2 )
            }
            Text("Profile Screen", style = appTypography().h4)

            Button(onClick = { navigator.pop() }) {
                Text("Go Back")
            }
        }
    }
}