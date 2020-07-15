package com.example.loginapp.common.data

sealed class LoadingStatus<out R> {
    object Idle : LoadingStatus<Nothing>()
    data class Loading(val message: String) : LoadingStatus<Nothing>()
    data class Error(val message: String, val error: Throwable?) : LoadingStatus<Nothing>()
    data class Success<out T>(val data: T) : LoadingStatus<T>()
}

/**
 * `true` if [LoadingStatus] is of type [LoadingStatus.Success] & holds non-null [LoadingStatus.Success.data].
 */
val LoadingStatus<*>.succeeded
    get() = this is LoadingStatus.Success && data != null

val LoadingStatus<*>.loading
    get() = this is LoadingStatus.Loading