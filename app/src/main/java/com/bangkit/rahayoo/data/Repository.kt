package com.bangkit.rahayoo.data

import com.bangkit.rahayoo.data.firebase.FirebaseDataSource
import com.bangkit.rahayoo.data.model.body.RegisterBody
import com.bangkit.rahayoo.data.model.response.MessageResponse
import com.bangkit.rahayoo.data.remote.ApiService
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.tasks.await

class Repository(
    private val apiService: ApiService,
    private val firebaseDataSource: FirebaseDataSource
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

    suspend fun registerUserOnServer(name: String, email: String, onSuccess: (message: MessageResponse) -> Unit, onFailure: (Exception) -> Unit) {
        val getTokenTask = firebaseDataSource.getCurrentUser()?.getIdToken(true)?.await()
        return if (getTokenTask?.token != null) {
            val registerBody = RegisterBody(name, email)
            val bearerToken = getTokenTask.token.toString()
            val response = apiService.register(bearerToken, registerBody)
            if (response.isSuccessful) {
                onSuccess(response.body()!!)
            } else {
                onSuccess(MessageResponse(response.message()))
            }
        } else {
            onFailure(Exception("Failed to retrieve firebase id token"))
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(apiService: ApiService, firebaseDataSource: FirebaseDataSource): Repository =
            INSTANCE ?: synchronized(this) {
                Repository(apiService, firebaseDataSource).apply {
                    INSTANCE = this
                }
            }
    }
}