package org.example.project.presentation.components

/*
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import org.example.project.presentation.viewmodel.UiMessageHandler
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.collectAsState
import org.example.project.presentation.viewmodel.UiMessage
import utils.MessageType

@Composable
fun HandleUiMessages(uiMessageHandler: UiMessageHandler) {
    val uiMessage by uiMessageHandler.uiMessage.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(uiMessage) {
        uiMessage?.let {
            when (it) {
                is UiMessage.SnackbarMessage -> {
                    snackbarHostState.showSnackbar(
                        message = it.message,
                        backgroundColor = getMessageColor(it.type)
                    )
                }

                is UiMessage.ToastMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
            uiMessageHandler.clearMessage()
        }
    }

    SnackbarHost(hostState = snackbarHostState)
}

@Composable
fun getMessageColor(type: MessageType): Color {
    return when (type) {
        MessageType.SUCCESS -> Color(0xFF4CAF50) // Green
        MessageType.ERROR -> Color(0xFFF44336) // Red
        MessageType.INFO -> Color(0xFF2196F3) // Blue
    }
}
*/
