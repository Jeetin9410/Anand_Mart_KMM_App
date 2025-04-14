package org.example.project.presentation.screens.profile_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.project.AnandMartDb
import org.example.project.config.AppConfig
import org.example.project.domain.model.UsersModel
import org.example.project.presentation.screens.login.checkRequiredFields
import org.example.project.theme.colors.AppColors
import org.example.project.theme.typography.appTypography
import org.koin.core.component.KoinComponent
import org.koin.core.component.get


class ProfileEditScreen(
    private val user: UsersModel,
    private val imageUrl: String) : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val db = getKoin().get<AnandMartDb>()
        val appConfig: AppConfig = get()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Edit Profile", style = appTypography().h6, color = Color.Black) },
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
            },
            bottomBar = {
                Button(
                    onClick = {
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = AppColors.buttonPrimary), //Color(0xFF435C3B)
                ) {
                    Text(
                        "Submit",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = appTypography().button
                    )
                }
            }
        ) {
            EditProfileScreen(
                name = "${user.name.firstname} ${user.name.lastname}",
                email = user.email,
                username = user.username,
                password = user.password,
                phoneNumber = user.phone,
                imageUrl = imageUrl,
                onNameChange = { },
                onPasswordChange = { },
                onPhoneChange = { }
            )
        }
    }
}

@Composable
fun EditProfileScreen(
    name: String,
    email: String,
    username: String,
    password: String,
    phoneNumber: String,
    imageUrl: String = "",
    onNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        ProfileImageWithEditIcon(imageUrl) {
            // Handle change
        }

        Spacer(modifier = Modifier.height(16.dp))

        ProfileTextField("Name", name, onNameChange)
        ProfileTextField("E mail address", email, onValueChange = {}, enabled = false)
        ProfileTextField("User name", username, onValueChange = {}, enabled = false)
        ProfileTextField("Password", password, onPasswordChange, isPassword = true)
        ProfileTextField("Phone number", phoneNumber, onPhoneChange)

        Spacer(modifier = Modifier.height(80.dp))
    }
}
