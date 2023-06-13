package com.bangkit.rahayoo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.rahayoo.data.Repository
import com.bangkit.rahayoo.ui.chat.ChatViewModel
import com.bangkit.rahayoo.ui.home.HomeViewModel
import com.bangkit.rahayoo.ui.login.LoginViewModel
import com.bangkit.rahayoo.ui.profile.ProfileViewModel
import com.bangkit.rahayoo.ui.profile.edit.EditProfileViewModel
import com.bangkit.rahayoo.ui.register.RegisterViewModel
import com.bangkit.rahayoo.ui.test.StressTestViewModel

class ViewModelFactory(private val repository: Repository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(StressTestViewModel::class.java) -> {
                StressTestViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(EditProfileViewModel::class.java) -> {
                EditProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ChatViewModel::class.java) -> {
                ChatViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown view model class: ${modelClass.name}")
        }
    }
}