package org.example.project.presentation.screens.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.benasher44.uuid.uuid4
import com.example.project.AnandMartDb
import createNotification
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import org.example.project.config.AppConfig
import org.example.project.presentation.screens.main_screen.MainScreen
import org.example.project.presentation.screens.login.LoginScreen
import org.example.project.presentation.viewmodel.LoginViewModel
import org.example.project.theme.typography.appTypography
import org.example.project.utils.AppConstants
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.mp.KoinPlatform

class SplashScreen : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val loginViewModel = koinViewModel<LoginViewModel>()
        var isSessionValid = false
        loginViewModel.checkIfSessionValid() { isValid ->
            if (isValid) {
                isSessionValid = true
            }
        }


        // Animation state
        var visible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            visible = true
            delay(3000) // 3-second splash delay
            if(loginViewModel.isUserLoggedIn() && isSessionValid){
                navigator.replace(MainScreen())
            } else {
                navigator.replace(LoginScreen())
            }
        }
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Image(painterResource(Res.drawable.compose_multiplatform), null, modifier = Modifier.size(300.dp))
            }
            Text("Anand Mart", style = appTypography().h4)
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
