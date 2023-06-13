package com.bangkit.rahayoo.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rahayoo.data.Repository
import com.bangkit.rahayoo.data.model.UiState
import com.bangkit.rahayoo.data.model.User
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository): ViewModel() {

    private val _userData = MutableLiveData<UiState<User>>()
    val userData: LiveData<UiState<User>>
        get() = _userData

    init {
        getUserData()
    }

    private fun getUserData() {
        _userData.value = UiState.Loading
        viewModelScope.launch {
            repository.getUserData({
                _userData.value = UiState.Success(it)
            }, {
                _userData.value = UiState.Error(it.message.toString())
            })
        }
    }
}