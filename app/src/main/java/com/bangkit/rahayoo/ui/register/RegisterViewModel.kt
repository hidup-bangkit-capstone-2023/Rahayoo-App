package com.bangkit.rahayoo.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rahayoo.data.Repository
import com.bangkit.rahayoo.data.model.AuthResult
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: Repository) : ViewModel() {

    private val _authResult = MutableLiveData<AuthResult<*>>()
    val authResult: MutableLiveData<AuthResult<*>>
        get() = _authResult

    fun registerFirebase(email: String, password: String) {
        _authResult.value = AuthResult.Loading
        viewModelScope.launch {
            repository.signUpWithEmailAndPassword(email, password, {
                _authResult.value = AuthResult.Success(it)
            }, {
                _authResult.value = AuthResult.Error(it.message.toString())
            })
        }
    }

    fun registerServer(name: String, email: String) {
        _authResult.value = AuthResult.Loading
        viewModelScope.launch {
            repository.registerUserOnServer(name, email, {
                _authResult.value = AuthResult.Success(it)
            }, {
                _authResult.value = AuthResult.Error(it.message.toString())
            })
        }
    }
}