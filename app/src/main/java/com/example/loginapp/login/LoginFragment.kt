package com.example.loginapp.login

import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment

class LoginFragment : DaggerFragment() {
    @dagger.Module
    abstract class Module {
        @ContributesAndroidInjector(modules = [])
        abstract fun contribute(): LoginFragment
    }
}
