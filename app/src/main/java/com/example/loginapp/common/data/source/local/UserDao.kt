package com.example.loginapp.common.data.source.local

import com.example.loginapp.common.data.model.User
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class UserDao {
    // in-memory cache of the user object
    private var user: User = User.NO_USER
    private val mutex = Mutex()

    suspend fun getUser(): User {
        val response = CompletableDeferred<User>()
        sendMessage(UserMsg.GetUser(response))
        return response.await()
    }

    suspend fun setUser(user: User) {
        sendMessage(UserMsg.SetUser(user))
    }

    private suspend fun sendMessage(msg: UserMsg) {
        mutex.withLock {
            when (msg) {
                is UserMsg.SetUser -> user = msg.user
                is UserMsg.GetUser -> msg.response.complete(user)
            }
        }
    }

    sealed class UserMsg {
        class SetUser(val user: User) : UserMsg() // one-way message to set user
        class GetUser(val response: CompletableDeferred<User>) : UserMsg() // a request with reply
    }
}
