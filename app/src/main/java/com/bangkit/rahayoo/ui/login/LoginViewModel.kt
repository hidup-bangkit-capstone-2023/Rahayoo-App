package com.bangkit.rahayoo.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rahayoo.data.Repository
import com.bangkit.rahayoo.data.model.UiState
import com.google.firebase.auth.AuthResult
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {

    private val _authResult = MutableLiveData<UiState<AuthResult>>()
    val authResult: MutableLiveData<UiState<AuthResult>>
        get() = _authResult

    fun login(email: String, password: String) {
        _authResult.value = UiState.Loading
        viewModelScope.launch {
            repository.signInWithEmailAndPassword(email, password, {
                _authResult.value = UiState.Success(it)
            }, {
                _authResult.value = UiState.Error(it.message.toString())
            })
        }
    }
}