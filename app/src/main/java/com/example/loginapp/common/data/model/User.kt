package com.example.loginapp.common.data.model

data class User(
    val userId: String = "",
    val displayName: String  = ""
) {
    val loggedIn: Boolean
        get() = userId.isNotEmpty()

    companion object {
        val NO_USER = User()
    }
}