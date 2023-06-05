package com.bangkit.rahayoo.data.model

import java.time.LocalDate

data class User(
    val uid: String,
    val name: String,
    val email: String,
    val address: String,
    val dateOfBirth: LocalDate,
    val age: Int,
    val departmentId: Int,
    val avatarUrl: String? = null,
)