package org.example.project.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.project.AnandMartDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.config.AppConfig
import org.example.project.domain.model.UsersModel
import org.example.project.domain.repository.LoginRepository
import org.example.project.domain.repository.SessionRepository

class ProfileViewModel(
    private val loginRepository: LoginRepository,
    private val session: SessionRepository,
    private val db: AnandMartDb
) : ViewModel() {
    fun getUserDetails() : UsersModel {
        return session.getUserDetails()
    }

    fun endSession() {
        CoroutineScope(Dispatchers.Default).launch {
            session.endSession()
        }
    }
}