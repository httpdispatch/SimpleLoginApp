package com.example.loginapp.profile

import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment

class ProfileFragment: DaggerFragment() {
    @dagger.Module
    abstract class Module {
        @ContributesAndroidInjector(modules = [])
        abstract fun contribute(): ProfileFragment
    }
}