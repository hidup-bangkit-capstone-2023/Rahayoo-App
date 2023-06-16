package com.bangkit.rahayoo.ui.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rahayoo.data.Repository
import com.bangkit.rahayoo.data.model.StressTestAnswer
import com.bangkit.rahayoo.data.model.StressTestQuestion
import com.bangkit.rahayoo.data.model.UiState
import com.bangkit.rahayoo.data.model.response.MessageResponse
import com.bangkit.rahayoo.data.model.response.MessageResponseWithUserId
import com.bangkit.rahayoo.data.model.response.WeeklyStatusResponse
import kotlinx.coroutines.launch

class StressTestViewModel(private val repository: Repository) : ViewModel() {

    private val _uiState: MutableLiveData<UiState<MessageResponse>> =
        MutableLiveData()
    val uiState: LiveData<UiState<MessageResponse>>
        get() = _uiState

    private val _userData = MutableLiveData<WeeklyStatusResponse>()
    val userData: LiveData<WeeklyStatusResponse>
        get() = _userData

    fun submitAnswer(list: StressTestAnswer) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            repository.submitStressTestAnswer(list, {
                _uiState.value = UiState.Success(it)
            }, {
                _uiState.value = UiState.Error(it.message ?: "Unknown Error")
            })
        }
    }

    fun getUserData(onSuccess: () -> Unit) {
        viewModelScope.launch {
            repository.getUserWeeklyCalendar({
                _userData.value = it
                onSuccess()
            }, {
                // Show Error
            })
        }
    }
}