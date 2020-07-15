package com.example.loginapp.common.data.source

import android.content.Context
import javax.inject.Inject

class DefaultResourceDataSource @Inject constructor(
    private val applicationContext: Context
) : ResourceDataSource {
    override fun getString(id: Int): String {
        return applicationContext.getString(id)
    }

}