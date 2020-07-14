package com.example.loginapp

import com.example.loginapp.common.data.DataModule
import com.example.loginapp.common.lifecycle.ViewModelBuilder
import com.example.loginapp.common.ui.UiModule
import dagger.Module

/**
 * The Dagger dependency injection module for the application
 */
@Module(includes = [
    UiModule::class,
    DataModule::class,
    ViewModelBuilder::class])
class ApplicationModule {
}