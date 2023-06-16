package com.bangkit.rahayoo.data.firebase

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseDataSource {

    private val firebaseAuth = Firebase.auth

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: (AuthResult) -> Unit,
        onFailure: (Exception) -> Unit,
    ) {
        firebaseAuth.signInWithEmailAndPassword(
            email, password
        )
            .addOnSuccessListener(onSuccess)
            .addOnFailureListener(onFailure)
    }

    fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: (AuthResult) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(
            email, password
        )
            .addOnSuccessListener(onSuccess)
            .addOnFailureListener(onFailure)
    }

    fun getCurrentUser() = firebaseAuth.currentUser

    fun signOutUser() = firebaseAuth.signOut()
}