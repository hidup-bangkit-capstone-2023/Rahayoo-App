package com.bangkit.rahayoo.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rahayoo.data.Repository
import com.bangkit.rahayoo.data.model.UiState
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: Repository) : ViewModel() {

    private val _authResult = MutableLiveData<UiState<*>>()
    val authResult: MutableLiveData<UiState<*>>
        get() = _authResult

    fun registerFirebase(email: String, password: String) {
        _authResult.value = UiState.Loading
        viewModelScope.launch {
            repository.signUpWithEmailAndPassword(email, password, {
                _authResult.value = UiState.Success(it)
            }, {
                _authResult.value = UiState.Error(it.message.toString())
            })
        }
    }

    fun registerServer(name: String, email: String) {
        _authResult.value = UiState.Loading
        viewModelScope.launch {
            repository.registerUserOnServer(name, email, {
                _authResult.value = UiState.Success(it)
            }, {
                _authResult.value = UiState.Error(it.message.toString())
            })
        }
    }
}