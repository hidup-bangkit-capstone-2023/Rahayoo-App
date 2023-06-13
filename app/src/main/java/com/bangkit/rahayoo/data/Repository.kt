package com.bangkit.rahayoo.data

import com.bangkit.rahayoo.data.firebase.FirebaseDataSource
import com.bangkit.rahayoo.data.local.Preferences
import com.bangkit.rahayoo.data.model.StressTestAnswer
import com.bangkit.rahayoo.data.model.StressTestQuestion
import com.bangkit.rahayoo.data.model.User
import com.bangkit.rahayoo.data.model.body.RegisterBody
import com.bangkit.rahayoo.data.model.body.UserBody
import com.bangkit.rahayoo.data.model.response.MessageResponse
import com.bangkit.rahayoo.data.model.response.MessageResponseWithUserId
import com.bangkit.rahayoo.data.model.response.StressLevelResponse
import com.bangkit.rahayoo.data.remote.ApiService
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.tasks.await

class Repository(
    private val apiService: ApiService,
    private val firebaseDataSource: FirebaseDataSource,
    private val preferences: Preferences
) {
    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: (AuthResult) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firebaseDataSource.signInWithEmailAndPassword(email, password, onSuccess, onFailure)
    }

    fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: (AuthResult) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        firebaseDataSource.signUpWithEmailAndPassword(email, password, onSuccess, onFailure)
    }

    fun isUserLoggedIn(): Boolean {
        return firebaseDataSource.getCurrentUser() != null
    }

    suspend fun registerUserOnServer(name: String, email: String, onSuccess: (message: MessageResponseWithUserId) -> Unit, onFailure: (Exception) -> Unit) {
        val getTokenTask = firebaseDataSource.getCurrentUser()?.getIdToken(true)?.await()
        return if (getTokenTask?.token != null) {
            val registerBody = RegisterBody(name, email)
            val response = apiService.register(getTokenTask.token!!, registerBody)
            if (response.isSuccessful) {
                preferences.saveUserId(response.body()!!.userId)
                onSuccess(response.body()!!)
            } else {
                onFailure(Exception(response.message()))
            }
        } else {
            onFailure(Exception("Failed to retrieve firebase id token"))
        }
    }

    suspend fun submitStressTestAnswer(answers: List<StressTestAnswer>, onSuccess: (message: MessageResponseWithUserId) -> Unit, onFailure: (Exception) -> Unit) {
        val getTokenTask = firebaseDataSource.getCurrentUser()?.getIdToken(true)?.await()
        return if (getTokenTask?.token != null) {
            val userId = preferences.getUserId()
            val response = apiService.submitStressTestAnswer(getTokenTask.token!!, answers, userId!!)
            if (response.isSuccessful) {
                onSuccess(response.body()!!)
            } else {
                onFailure(Exception(response.message()))
            }
        } else {
            onFailure(Exception("Failed to retrieve firebase id token"))
        }
    }

    suspend fun getUserData(onSuccess: (User) -> Unit, onFailure: (Exception) -> Unit) {
        val getTokenTask = firebaseDataSource.getCurrentUser()?.getIdToken(true)?.await()
        return if (getTokenTask?.token != null) {
            val userId = preferences.getUserId()
            val response = apiService.getUserData(getTokenTask.token!!, userId!!)
            if (response.isSuccessful) {
                onSuccess(response.body()!!)
            } else {
                onFailure(Exception(response.message()))
            }
        } else {
            onFailure(Exception("Failed to retrieve firebase id token"))
        }
    }

    suspend fun updateUserData(userBody: UserBody, onSuccess: (message: MessageResponse) -> Unit, onFailure: (Exception) -> Unit) {
        val getTokenTask = firebaseDataSource.getCurrentUser()?.getIdToken(true)?.await()
        return if (getTokenTask?.token != null) {
            val userId = preferences.getUserId()
            val response = apiService.updateUserData(getTokenTask.token!!, userBody, userId!!)
            if (response.isSuccessful) {
                onSuccess(response.body()!!)
            } else {
                onFailure(Exception(response.message()))
            }
        } else {
            onFailure(Exception("Failed to retrieve firebase id token"))
        }
    }

    suspend fun getUserWeeklyStressLevel(onSuccess: (StressLevelResponse) -> Unit, onFailure: (Exception) -> Unit) {
        val getTokenTask = firebaseDataSource.getCurrentUser()?.getIdToken(true)?.await()
        return if (getTokenTask?.token != null) {
            val userId = preferences.getUserId()
            val response = apiService.getWeeklyStressLevel(getTokenTask.token!!, userId!!)
            if (response.isSuccessful) {
                onSuccess(response.body()!!)
            } else {
                onFailure(Exception(response.message()))
            }
        } else {
            onFailure(Exception("Failed to retrieve firebase id token"))
        }
    }

    fun signOutUser() {
        preferences.clearUserData()
        firebaseDataSource.signOutUser()
    }

    companion object {
        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(apiService: ApiService, firebaseDataSource: FirebaseDataSource, preferences: Preferences): Repository =
            INSTANCE ?: synchronized(this) {
                Repository(apiService, firebaseDataSource, preferences).apply {
                    INSTANCE = this
                }
            }
    }
}