package com.example.loginapp.common.ui

import com.example.loginapp.login.LoginFragment
import com.example.loginapp.profile.ProfileFragment
import dagger.Module

/**
 * The Dagger dependency injection module for the UI layer
 */
@Module(includes = [LoginFragment.Module::class, ProfileFragment.Module::class])
class UiModule