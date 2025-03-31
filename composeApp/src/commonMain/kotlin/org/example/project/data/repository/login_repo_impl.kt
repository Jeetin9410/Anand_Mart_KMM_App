package org.example.project.data.repository

import org.example.project.domain.model.UsersModel
import org.example.project.domain.repository.LoginRepository
import org.example.project.network.ApiClient

class LoginRepositoryImpl(private val apiClient: ApiClient) : LoginRepository {
    override suspend fun getAllUsers(): List<UsersModel> {
        return apiClient.getAllUsers()
    }
}