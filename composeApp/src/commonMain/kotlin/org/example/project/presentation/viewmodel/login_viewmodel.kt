package org.example.project.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.config.AppConfig
import org.example.project.domain.model.UsersModel
import org.example.project.domain.repository.LoginRepository
import org.example.project.network.ApiClient

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val appConfig: AppConfig,
    ) : ViewModel() {

    private val _allUsers = MutableStateFlow<List<UsersModel>>(emptyList())
    val allUsers: StateFlow<List<UsersModel>> = _allUsers

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    suspend fun fetchAllUsers() : List<UsersModel> {
        withContext(Dispatchers.IO) {
            _isLoading.value = true
            try {
                _allUsers.value = loginRepository.getAllUsers()
            } catch (e: Exception) {
                println("Error fetching user details: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }

        return _allUsers.value;
    }

}