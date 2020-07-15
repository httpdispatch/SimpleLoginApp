package com.example.loginapp.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginapp.R
import com.example.loginapp.common.data.LoadingStatus
import com.example.loginapp.common.data.source.ResourceDataSource
import com.example.loginapp.common.data.source.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val resourceDataSource: ResourceDataSource
) : ViewModel() {
    private val _viewState =
        MutableStateFlow<LoginViewState>(LoginViewState(LoadingStatus.Idle, "", ""))

    val viewState: StateFlow<LoginViewState> = _viewState

    fun login() {
        Timber.d("login() called")
        if (_viewState.value.loading) {
            return
        }
        _viewState.apply {
            value =
                value.copy(status = LoadingStatus.Loading(resourceDataSource.getString(R.string.login_logging_in)))
        }

        viewModelScope.launch {
            try {
                _viewState.value.run {
                    userRepository.login(userName, password)
                        .let {
                            _viewState.apply {
                                value = value.copy(status = LoadingStatus.Success(it))
                            }
                        }
                }
            } catch (t: Throwable) {
                _viewState.apply {
                    value = value.copy(
                        status = LoadingStatus.Error(
                            resourceDataSource.getString(R.string.login_error), t
                        )
                    )
                }
            }
        }
    }

    fun setUserName(newValue: String) {
        Timber.d("setUserName() called: newValue = $newValue")
        _viewState.apply {
            value = value.copy(
                userName = newValue
            )
        }
    }

    fun setPassword(newValue: String) {
        Timber.d("setPassword() called: newValue = $newValue")
        _viewState.apply {
            value = value.copy(
                password = newValue
            )
        }
    }
}