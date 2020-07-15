package com.example.loginapp.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginapp.R
import com.example.loginapp.common.data.LoadingStatus
import com.example.loginapp.common.data.model.User
import com.example.loginapp.common.data.source.ResourceDataSource
import com.example.loginapp.common.data.source.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val resourceDataSource: ResourceDataSource
) : ViewModel() {
    private val _viewState =
        MutableStateFlow<ProfileViewState>(ProfileViewState(LoadingStatus.Idle))

    val viewState: StateFlow<ProfileViewState> = _viewState

    init {
        start()
    }

    fun start() {
        if (_viewState.value.loading) {
            return
        }
        _viewState.apply {
            value =
                value.copy(status = LoadingStatus.Loading(resourceDataSource.getString(R.string.profile_loading_user)))
        }

        viewModelScope.launch {
            try {
                userRepository.getUser()
                    .let {
                        _viewState.apply { value = value.copy(status = LoadingStatus.Success(it)) }
                    }
            } catch (t: Throwable) {
                _viewState.apply {
                    value = value.copy(
                        status = LoadingStatus.Error(
                            resourceDataSource.getString(R.string.profile_load_user_error), t
                        )
                    )
                }
            }
        }
    }

    fun logout() {
        if (_viewState.value.loading) {
            return
        }
        _viewState.apply {
            value =
                value.copy(status = LoadingStatus.Loading(resourceDataSource.getString(R.string.profile_logging_out)))
        }
        viewModelScope.launch {
            try {
                userRepository.logout()
                    .let {
                        _viewState.apply {
                            value =
                                value.copy(status = LoadingStatus.Success(User.NO_USER))
                        }
                    }
            } catch (t: Throwable) {
                _viewState.apply {
                    value = value.copy(
                        status = LoadingStatus.Error(
                            resourceDataSource.getString(R.string.profile_logout_error), t
                        )
                    )
                }
            }
        }
    }
}