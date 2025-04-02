package org.example.project.presentation.screens.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.presentation.viewmodel.LoginViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.TablerIcons
import compose.icons.tablericons.Eye
import compose.icons.tablericons.EyeOff
import createNotification
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.apple
import kotlinproject.composeapp.generated.resources.facebook
import kotlinproject.composeapp.generated.resources.google
import kotlinx.coroutines.launch
import org.example.project.config.AppConfig
import org.example.project.domain.model.UsersModel
import org.example.project.presentation.screens.main_screen.MainScreen
import org.example.project.theme.colors.AppColors
import org.example.project.theme.typography.appTypography
import org.example.project.utils.LoadingOverlay
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class LoginScreen : Screen, KoinComponent {

    companion object {
        const val ARG_IS_LOGGED_IN = "arg_is_logged_in"
        const val ARG_EMAIL = "email"
        const val ARG_PASSWORD = "password"
    }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val loginViewModel = koinViewModel<LoginViewModel>()
        val appConfig: AppConfig = get()
        val isLoading by loginViewModel.isLoading.collectAsState(false)
        val coroutineScope = rememberCoroutineScope()


        LoadingOverlay(isLoading = isLoading) {
            Column {
                Spacer(modifier = Modifier.height(50.dp))
                LoginScreenUi { (email, password) ->
                    coroutineScope.launch {
                        val users = loginViewModel.fetchAllUsers() // Wait for result

                        val matchedUsers = users.filter { it.email == email && it.password == password }
                        if (matchedUsers.isEmpty()) {
                            val notification = createNotification(NotificationType.TOAST)
                            notification.show("Incorrect email or password!")
                        } else {
                            appConfig.putBoolean(ARG_IS_LOGGED_IN, true)
                            appConfig.putString(ARG_EMAIL, email)
                            appConfig.putString(ARG_PASSWORD, password)
                            navigator.replace(MainScreen())
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun LoginScreenUi(onTapLog : (Pair<String,String>) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Welcome back!\nGlad to see you,\nAgain!",
            style = appTypography().h4,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Enter your email") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            textStyle = appTypography().body1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = AppColors.accent,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color(0xFF4A5A48)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Enter your password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = if(!passwordVisible) TablerIcons.Eye else TablerIcons.EyeOff, null,  modifier = Modifier.size(24.dp))
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            textStyle = appTypography().body1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = AppColors.accent,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color(0xFF4A5A48)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { /* Handle forgot password */ }) {
            Text("Forgot Password?", color = Color.Gray, fontWeight = FontWeight.Bold, style = appTypography().body2)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                checkRequiredFields(email, password,
                    { onTapLog.invoke(Pair(email, password)) })
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = AppColors.buttonPrimary), //Color(0xFF435C3B)
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "Login",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = appTypography().button
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Or Login with", color = Color.Gray, textAlign = TextAlign.Center, style = appTypography().body2)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            SocialLoginButton(iconRes = painterResource(Res.drawable.google)) // Replace with Facebook icon
            SocialLoginButton(iconRes = painterResource(Res.drawable.facebook)) // Replace with Google icon
            SocialLoginButton(iconRes = painterResource(Res.drawable.apple)) // Replace with Apple icon
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = {
            val notification = createNotification(NotificationType.TOAST, )
            notification.show("Hello, World!")
        }) {
            Text("Don't have an account? Register Now", color = Color(0xFF435C3B), fontWeight = FontWeight.Bold, style = appTypography().body2)
        }
    }
}

fun checkValidUserOrNot(allUsers: List<UsersModel>, email: String, password: String) {

}

fun checkRequiredFields(email : String, password: String, onTapLog: () -> Unit) {
    val notification = createNotification(NotificationType.TOAST, )
    if(email.isEmpty()) {
        notification.show("Email is required!")

    } else if (password.isEmpty()) {
        notification.show("Password is required!")

    } else {
        onTapLog.invoke()
    }
}

@Composable
fun SocialLoginButton(iconRes: Painter) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = iconRes, contentDescription = "Social login icon",  modifier = Modifier.size(30.dp), )
    }
}