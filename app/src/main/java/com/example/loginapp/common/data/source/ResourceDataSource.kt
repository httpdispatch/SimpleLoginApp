package com.example.loginapp.common.data.source

interface ResourceDataSource {
    fun getString(id: Int): String
}