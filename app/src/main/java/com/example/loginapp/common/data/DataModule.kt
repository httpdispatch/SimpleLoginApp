package com.example.loginapp.common.data

import com.example.loginapp.common.data.source.DefaultUserRepository
import com.example.loginapp.common.data.source.LoginDataSource
import com.example.loginapp.common.data.source.UserDataSource
import com.example.loginapp.common.data.source.UserRepository
import com.example.loginapp.common.data.source.local.UserDao
import com.example.loginapp.common.data.source.local.UserLocalDataSource
import com.example.loginapp.common.data.source.remote.LoginRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module(includes = [DataModuleBinds::class])
class DataModule {
    @Singleton
    @Provides
    fun provideLoginDataSource(): LoginDataSource {
        return LoginRemoteDataSource()
    }

    @Singleton
    @Provides
    fun provideUserDataSource(
        userDao: UserDao,
        ioDispatcher: CoroutineDispatcher
    ): UserDataSource {
        return UserLocalDataSource(userDao, ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}

@Module
abstract class DataModuleBinds {

    @Singleton
    @Binds
    abstract fun bindRepository(repo: DefaultUserRepository): UserRepository
}
