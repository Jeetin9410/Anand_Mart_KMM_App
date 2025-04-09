package org.example.project.presentation.screens.main_screen.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import org.example.project.presentation.screens.profile_screen.ProfileEditScreen
import org.example.project.presentation.screens.profile_screen.ProfileScreenUi
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


        val profileWithDefault =
            appConfig.getObject(AppConstants.ARG_USER_DETAILS, UsersModel.serializer())
                ?: UsersModel.empty()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Profile", style = appTypography().h6, color = Color.Black) },
                    backgroundColor = Color.White,
                    elevation = 5.dp,
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "Back",
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                )
            }
        ) {
            ProfileScreenUi(
                userName = profileWithDefault.username,
                email = profileWithDefault.email,
                profileImage = "https://i.pravatar.cc/300",
                onEditClick = {
                    navigator.push(
                        ProfileEditScreen(
                            profileWithDefault,
                            "https://i.pravatar.cc/300"
                        )
                    )
                },
                onItemClicked = { item ->

                }
            )
        }


    }
}