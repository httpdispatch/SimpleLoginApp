package com.example.loginapp.common.data.source

import com.example.loginapp.common.data.model.User

interface LoginDataSource {

    suspend fun login(username: String, password: String): User

    suspend fun logout()
}