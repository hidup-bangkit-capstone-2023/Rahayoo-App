package com.bangkit.rahayoo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rahayoo.data.Repository
import com.bangkit.rahayoo.data.model.UiState
import com.bangkit.rahayoo.data.model.User
import com.bangkit.rahayoo.data.model.response.StressLevelResponse
import com.bangkit.rahayoo.data.model.response.WeeklyStatusResponse
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _user = MutableLiveData<UiState<User>>()
    val user: LiveData<UiState<User>>
        get() = _user

    private val _userStressLevelData = MutableLiveData<UiState<WeeklyStatusResponse>>()
    val userStressLevelData: LiveData<UiState<WeeklyStatusResponse>>
        get() = _userStressLevelData

    init {
        getUserData()
        getUserWeeklyCalendar()
    }

    fun getUserData() {
        _user.value = UiState.Loading
        viewModelScope.launch {
            repository.getUserData({
                _user.value = UiState.Success(it)
            }, {
                _user.value = UiState.Error(it.message.toString())
            })
        }
    }

    fun getUserWeeklyCalendar() {
        _userStressLevelData.value = UiState.Loading
        viewModelScope.launch {
            repository.getUserWeeklyCalendar({
                _userStressLevelData.value = UiState.Success(it)
            }, {
                _userStressLevelData.value = UiState.Error(it.message.toString())
            })
        }
    }
}