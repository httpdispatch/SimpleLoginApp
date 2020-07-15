package com.example.loginapp.common.data

import android.content.Context
import android.content.SharedPreferences
import com.example.loginapp.common.data.source.*
import com.example.loginapp.common.data.source.local.UserDao
import com.example.loginapp.common.data.source.local.UserLocalDataSource
import com.example.loginapp.common.data.source.remote.LoginRemoteDataSource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
    fun provideSharedPreferencesi(context: Context): SharedPreferences {
        return context.getSharedPreferences("app", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

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

    @Singleton
    @Binds
    abstract fun bindResources(resources: DefaultResourceDataSource): ResourceDataSource
}
