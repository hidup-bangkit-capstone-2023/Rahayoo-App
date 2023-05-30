package com.bangkit.rahayoo.di

import com.bangkit.rahayoo.data.Repository
import com.bangkit.rahayoo.data.firebase.FirebaseDataSource
import com.bangkit.rahayoo.data.remote.RahayooApi

object Injection {
    fun provideRepository(): Repository {
        val apiService = RahayooApi.apiService
        val firebaseDataSource = FirebaseDataSource()
        return Repository.getInstance(apiService, firebaseDataSource)
    }
}