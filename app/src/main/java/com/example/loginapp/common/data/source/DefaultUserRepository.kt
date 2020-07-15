package com.example.loginapp.common.data.source

import com.example.loginapp.common.data.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(
    private val loginDataSource: LoginDataSource,
    private val userDataSource: UserDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepository {

    private val cachedUser: AtomicReference<User?> = AtomicReference(null)

    override suspend fun getUser(): User {
        return withContext(ioDispatcher) {
            cachedUser.get()
                ?: userDataSource.getUser()
                    .also {
                        cachedUser.set(it)
                    }
        }
    }

    override suspend fun login(username: String, password: String): User {
        return withContext(ioDispatcher) {
            val user = loginDataSource.login(username, password)
            userDataSource.saveUser(user)
            cachedUser.set(user)
            return@withContext user
        }
    }

    override suspend fun logout() {
        return withContext(ioDispatcher) {
            loginDataSource.logout()
            userDataSource.forgetUser()
            cachedUser.set(null)
        }
    }
}
