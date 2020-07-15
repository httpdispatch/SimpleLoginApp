package com.example.loginapp.common.data.source.local

import com.example.loginapp.common.data.model.User
import com.example.loginapp.common.data.source.UserDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserLocalDataSource internal constructor(
    private val userDao: UserDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserDataSource {

    override suspend fun getUser(): User = withContext(ioDispatcher) {
        return@withContext userDao.getUser()
    }

    override suspend fun saveUser(user: User) = withContext(ioDispatcher) {
        userDao.setUser(user)
    }

    override suspend fun forgetUser() {
        saveUser(User())
    }
}
