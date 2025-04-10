package org.example.project.domain.repository

import com.example.project.Session
import org.example.project.domain.model.UsersModel

interface SessionRepository{
    suspend fun startSession()
    suspend fun endSession()
    suspend fun currentActiveSession(): Session
    fun getIsUserLoggedIn(): Boolean
    fun setUserLoggedIn(): Boolean
    fun getUserDetails(): UsersModel
    fun setUserDetails(user: UsersModel): Boolean

}