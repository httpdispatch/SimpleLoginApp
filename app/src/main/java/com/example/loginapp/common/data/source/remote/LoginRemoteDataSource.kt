package com.example.loginapp.common.data.source.remote

import com.example.loginapp.common.data.model.User
import com.example.loginapp.common.data.source.LoginDataSource
import kotlinx.coroutines.delay
import kotlin.random.Random

class LoginRemoteDataSource : LoginDataSource {
    override suspend fun login(username: String, password: String): User {
        delay(SERVICE_LATENCY_IN_MILLIS)
        return if (Random.nextInt(0, 100) > 10)
            User("id", username)
        else
            throw Exception("Random exception")
    }

    override suspend fun logout() {
        // do nothing
    }

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS = 2000L
    }
}