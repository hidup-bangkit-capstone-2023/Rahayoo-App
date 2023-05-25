package com.bangkit.rahayoo.data.model

sealed class AuthResult<out R> {
    data class Success<out T>(val data: T) : AuthResult<T>()
    data class Error(val error: String) : AuthResult<Nothing>()
    object Loading : AuthResult<Nothing>()
}