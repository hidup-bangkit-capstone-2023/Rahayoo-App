package com.bangkit.rahayoo.di

import android.content.Context
import com.bangkit.rahayoo.data.Repository
import com.bangkit.rahayoo.data.firebase.FirebaseDataSource
import com.bangkit.rahayoo.data.local.Preferences
import com.bangkit.rahayoo.data.remote.RahayooApi

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = RahayooApi.apiService
        val firebaseDataSource = FirebaseDataSource()
        val preferences = Preferences(context)
        return Repository.getInstance(apiService, firebaseDataSource, preferences)
    }
}