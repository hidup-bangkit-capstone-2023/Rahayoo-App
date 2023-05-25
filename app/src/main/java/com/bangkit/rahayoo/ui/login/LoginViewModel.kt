package com.bangkit.rahayoo.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.rahayoo.data.model.AuthResult
import com.bangkit.rahayoo.data.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {

    private val auth = Firebase.auth

    private val _authResult = MutableLiveData<AuthResult<*>>()
    val authResult: MutableLiveData<AuthResult<*>>
        get() = _authResult

    fun login(email: String, password: String) {
        _authResult.value = AuthResult.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                if (it.user != null) {
                    val user = User(
                        uid = it.user!!.uid,
                        email = it.user!!.email!!,
                        name = it.user!!.displayName!!,
                    )
                    _authResult.value = AuthResult.Success(user)
                }
            }
            .addOnFailureListener {
                _authResult.value = AuthResult.Error(it.message.toString())
            }
    }
}