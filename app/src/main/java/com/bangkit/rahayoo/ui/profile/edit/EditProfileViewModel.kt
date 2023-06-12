package com.bangkit.rahayoo.ui.profile.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rahayoo.data.Repository
import com.bangkit.rahayoo.data.model.UiState
import com.bangkit.rahayoo.data.model.User
import com.bangkit.rahayoo.data.model.body.UserBody
import com.bangkit.rahayoo.data.model.response.MessageResponse
import kotlinx.coroutines.launch

class EditProfileViewModel(private val repository: Repository) : ViewModel() {

    private val _userData = MutableLiveData<UiState<User>>()
    val userData: LiveData<UiState<User>>
        get() = _userData

    private val _updateStatus = MutableLiveData<UiState<MessageResponse>>()
    val updateStatus: LiveData<UiState<MessageResponse>>
        get() = _updateStatus

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

    fun editProfile(userBody: UserBody) {
        _updateStatus.value = UiState.Loading
        viewModelScope.launch {
            repository.updateUserData(userBody, {
                _updateStatus.value = UiState.Success(it)
            }, {
                _updateStatus.value = UiState.Error(it.message.toString())
            })
        }
    }
}