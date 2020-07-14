package com.example.loginapp

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class CustomApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.factory().create(applicationContext)
    }
}