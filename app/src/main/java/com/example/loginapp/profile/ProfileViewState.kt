package com.example.loginapp.profile

import com.example.loginapp.common.data.LoadingStatus
import com.example.loginapp.common.data.loading
import com.example.loginapp.common.data.model.User

data class ProfileViewState(val status: LoadingStatus<User>) {
    val userName: String
        get() = if (status is LoadingStatus.Success) {
            status.data.displayName
        } else {
            ""
        }

    val loggedIn: Boolean
        get() = if (status is LoadingStatus.Success) {
            status.data.loggedIn
        } else {
            false
        }
    val loading: Boolean
        get() = status.loading

    val error: Boolean
        get() = status is LoadingStatus.Error

    val loadingMessage: String
        get() = if (status is LoadingStatus.Loading) {
            status.message
        } else {
            ""
        }

    val errorMessage: String
        get() = if (status is LoadingStatus.Error) {
            status.message
        } else {
            ""
        }
}