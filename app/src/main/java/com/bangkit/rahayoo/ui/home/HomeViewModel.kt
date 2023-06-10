package com.bangkit.rahayoo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rahayoo.data.Repository
import com.bangkit.rahayoo.data.model.UiState
import com.bangkit.rahayoo.data.model.User
import com.bangkit.rahayoo.data.model.response.StressLevelResponse
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _user = MutableLiveData<UiState<User>>()
    val user: LiveData<UiState<User>>
        get() = _user

    private val _userStressLevelData = MutableLiveData<UiState<StressLevelResponse>>()
    val userStressLevelData: LiveData<UiState<StressLevelResponse>>
        get() = _userStressLevelData

    init {
        getUserData()
        getUserStressLevelData()
    }

    private fun getUserData() {
        _user.value = UiState.Loading
//        viewModelScope.launch {
//            repository.getUserData({
//                _user.value = UiState.Success(it)
//            }, {
//                _user.value = UiState.Error(it.message.toString())
//            })
//        }
    }

    private fun getUserStressLevelData() {
        _userStressLevelData.value = UiState.Loading
        /* Uncomment when repo is done
        viewModelScope.launch {
            repository.getEmployeeStressLevel({
                _userStressLevelData.value = UiState.Success(it)
            }, {
                _userStressLevelData.value = UiState.Error(it.message.toString())
            })
        }

         */
    }

    fun signOut() {
        repository.signOutUser()
    }
}