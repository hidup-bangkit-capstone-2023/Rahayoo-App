package com.bangkit.rahayoo.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rahayoo.data.Repository
import com.bangkit.rahayoo.data.model.Chat
import com.bangkit.rahayoo.data.model.UiState
import com.bangkit.rahayoo.data.model.body.ChatBody
import kotlinx.coroutines.launch

class ChatViewModel(private val repository: Repository) : ViewModel() {

    private val _chatLog = MutableLiveData<MutableList<Chat>>()
    val chatLog: LiveData<MutableList<Chat>>
        get() = _chatLog

    private var chatBotCounter = 0

    private val chatLogs = mutableListOf<Chat>()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        startChatSession()
    }

    private fun startChatSession() {
        chatLogs.add(
            Chat(
                "chat_bot_0",
                "rahayoo.ai",
                "Hi, i’am rahayoo.ai, please feel free to tell me how is your day. All of what you share with me will not be shared to your company so your privacy is save with me."
            )
        )
        _chatLog.value = chatLogs
    }

    fun sendMessage(chat: Chat) {
        chatLogs.add(chat)
        _chatLog.value = chatLogs
        _isLoading.value = true
        viewModelScope.launch {
            val chatBody = ChatBody(chat.message)
            repository.sendMessage(chatBody, {
                chatLogs.add(
                    Chat(
                        "chat_bot_$chatBotCounter",
                        "rahayoo.ai",
                        it.answer
                    )
                )
                _chatLog.value = chatLogs
            }, {
                // Show Error Messages
            })

            _isLoading.value = false
        }
    }
}