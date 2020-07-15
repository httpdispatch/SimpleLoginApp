package com.example.loginapp.common.data.source

import com.example.loginapp.common.data.model.User

interface UserDataSource {

    suspend fun getUser(): User

    suspend fun saveUser(user: User)

    suspend fun forgetUser()
}
