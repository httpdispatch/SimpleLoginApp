package com.example.loginapp.common.data.source.local

import android.content.SharedPreferences
import com.example.loginapp.common.data.model.User
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber
import javax.inject.Inject

class UserDao @Inject constructor(val preferences: SharedPreferences, moshi: Moshi) {
    // in-memory cache of the user object
    companion object {
        const val USER_PREFERENCE_KEY = "user"
    }

    private val userAdapter = moshi.adapter<User>(User::class.java)
    private val mutex = Mutex()

    suspend fun getUser(): User {
        return try {
            val response = CompletableDeferred<User>()
            sendMessage(UserMsg.GetUser(response))
            response.await()
        } catch (t: Throwable) {
            Timber.e(t)
            User.NO_USER
        }
    }

    suspend fun setUser(user: User) {
        sendMessage(UserMsg.SetUser(user))
    }

    private suspend fun sendMessage(msg: UserMsg) {
        mutex.withLock {
            when (msg) {
                is UserMsg.SetUser -> userAdapter.toJson(msg.user)
                    .let { preferences.edit().putString(USER_PREFERENCE_KEY, it).commit() }
                is UserMsg.GetUser ->
                    preferences.getString(USER_PREFERENCE_KEY, "")
                        .let { userAdapter.fromJson(it!!) }
                        .let { msg.response.complete(it!!) }
            }
        }
    }

    sealed class UserMsg {
        class SetUser(val user: User) : UserMsg() // one-way message to set user
        class GetUser(val response: CompletableDeferred<User>) : UserMsg() // a request with reply
    }
}
