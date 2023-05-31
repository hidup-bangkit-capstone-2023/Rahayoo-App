package com.bangkit.rahayoo.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rahayoo.data.Repository
import com.bangkit.rahayoo.data.model.AuthResult
import com.bangkit.rahayoo.data.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {

    private val _authResult = MutableLiveData<AuthResult<*>>()
    val authResult: MutableLiveData<AuthResult<*>>
        get() = _authResult

    fun login(email: String, password: String) {
        _authResult.value = AuthResult.Loading
        viewModelScope.launch {
            repository.signInWithEmailAndPassword(email, password, {
                _authResult.value = AuthResult.Success(it)
            }, {
                _authResult.value = AuthResult.Error(it.message.toString())
            })
        }
    }
}