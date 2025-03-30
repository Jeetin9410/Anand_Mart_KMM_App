package org.example.project.presentation.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.utils.enums.MessageType

sealed class UiMessage {
    data class SnackbarMessage(val message: String, val type: MessageType) : UiMessage()
    data class ToastMessage(val message: String, val type: MessageType) : UiMessage()
}

class UiMessageHandler {
    private val _uiMessage = MutableStateFlow<UiMessage?>(null)
    val uiMessage = _uiMessage.asStateFlow()

    fun showSnackbar(message: String, type: MessageType) {
        _uiMessage.value = UiMessage.SnackbarMessage(message, type)
    }

    fun showToast(message: String, type: MessageType) {
        _uiMessage.value = UiMessage.ToastMessage(message, type)
    }

    fun clearMessage() {
        _uiMessage.value = null
    }
}
