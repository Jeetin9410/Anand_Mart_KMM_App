package org.example.project.domain.repository

import com.example.project.Session
import org.example.project.domain.model.AppSession
import org.example.project.domain.model.UsersModel

interface SessionRepository{
    suspend fun startSession()
    suspend fun endSession()
    fun currentActiveSession(): AppSession?
    fun getIsUserLoggedIn(): Boolean
    fun setUserLoggedIn(): Boolean
    fun getUserDetails(): UsersModel
    fun setUserDetails(user: UsersModel): Boolean

}