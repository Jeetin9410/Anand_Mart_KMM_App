package org.example.project.domain.repository

import org.example.project.domain.model.UsersModel

interface LoginRepository {

    suspend fun getAllUsers(): List<UsersModel>

}