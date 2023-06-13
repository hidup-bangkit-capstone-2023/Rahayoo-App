package com.bangkit.rahayoo.ui.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rahayoo.data.Repository
import com.bangkit.rahayoo.data.model.StressTestAnswer
import com.bangkit.rahayoo.data.model.StressTestQuestion
import com.bangkit.rahayoo.data.model.UiState
import com.bangkit.rahayoo.data.model.response.MessageResponseWithUserId
import kotlinx.coroutines.launch

class StressTestViewModel(private val repository: Repository) : ViewModel() {

    private val _uiState: MutableLiveData<UiState<MessageResponseWithUserId>> =
        MutableLiveData(UiState.Loading)
    val uiState: LiveData<UiState<MessageResponseWithUserId>>
        get() = _uiState

    fun submitAnswer(list: List<StressTestAnswer>) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            repository.submitStressTestAnswer(list, {
                _uiState.value = UiState.Success(it)
            }, {
                _uiState.value = UiState.Error(it.message ?: "Unknown Error")
            })
        }
    }
}