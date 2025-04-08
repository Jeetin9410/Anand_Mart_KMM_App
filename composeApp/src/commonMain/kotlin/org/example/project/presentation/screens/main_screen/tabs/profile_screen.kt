package org.example.project.presentation.screens.main_screen.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.project.AnandMartDb
import com.example.project.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.config.AppConfig
import org.example.project.domain.model.UsersModel
import org.example.project.theme.typography.appTypography
import org.example.project.utils.AppConstants
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class ProfileScreen : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val db = getKoin().get<AnandMartDb>()
        val appConfig: AppConfig = get()
        // var users by remember { mutableStateOf<List<User>>(emptyList()) }

        val profileWithDefault = appConfig.getObject(AppConstants.ARG_USER_DETAILS, UsersModel.serializer())
            ?: UsersModel.empty()

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

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Profile Screen", style = appTypography().h4)
            Text("UserDetails ${profileWithDefault.id} ${profileWithDefault.username} ${profileWithDefault.email} ${profileWithDefault.name} ${profileWithDefault.phone} ${profileWithDefault.address.city}", style = appTypography().subtitle2)
            Button(onClick = { navigator.pop() }) {
                Text("Go Back")
            }
        }
    }
}

fun readAllUsers() {

}