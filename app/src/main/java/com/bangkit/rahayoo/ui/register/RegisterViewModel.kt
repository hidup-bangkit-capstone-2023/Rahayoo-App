package com.bangkit.rahayoo.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.rahayoo.data.model.AuthResult
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterViewModel: ViewModel() {

    private val auth = Firebase.auth

    private val _authResult = MutableLiveData<AuthResult<*>>()
    val authResult: MutableLiveData<AuthResult<*>>
        get() = _authResult

    fun register(name: String, email: String, password: String) {
        _authResult.value = AuthResult.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                if (result.user != null) {
                    val userProfileChangeRequest = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()

                    result.user!!.updateProfile(userProfileChangeRequest)
                        .addOnSuccessListener {
                            _authResult.value = AuthResult.Success(result.user)
                        }
                }
            }
            .addOnFailureListener {
                _authResult.value = AuthResult.Error(it.message.toString())
            }
    }
}