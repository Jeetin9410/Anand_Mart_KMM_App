package org.example.project.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.benasher44.uuid.uuid4
import com.example.project.AnandMartDb
import com.example.project.Session
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import org.example.project.config.AppConfig
import org.example.project.domain.model.AppSession
import org.example.project.domain.model.UsersModel
import org.example.project.domain.repository.LoginRepository
import org.example.project.domain.repository.SessionRepository
import org.example.project.utils.AppConstants

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val session: SessionRepository,
    private val appConfig: AppConfig,
    private val db: AnandMartDb
) : ViewModel() {

    private val _allUsers = MutableStateFlow<List<UsersModel>>(emptyList())
    val allUsers: StateFlow<List<UsersModel>> = _allUsers

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    suspend fun fetchAllUsers(): List<UsersModel> {
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

    suspend fun startSession(userDetail: UsersModel) {
        session.setUserDetails(userDetail)
        session.setUserLoggedIn()
        session.startSession()
    }

    fun checkIfSessionValid(onResult: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            val result = try {
                db.transactionWithResult {
                    val session = db.sessionQueries.selectActive().executeAsOneOrNull()
                    session?.let {
                        val currentTime = Clock.System.now().toEpochMilliseconds()
                        val oneDayMillis = 24 * 60 * 60 * 1000L
                        val isSameDay = (currentTime - it.created_at) < oneDayMillis
                        it.is_active && isSameDay
                    } ?: false
                }
            } catch (e: Exception) {
                println("Error checking session validity: ${e.message}")
                false
            }

            onResult(result)
        }
    }

    fun isUserLoggedIn(): Boolean {
        return session.getIsUserLoggedIn()
    }
}