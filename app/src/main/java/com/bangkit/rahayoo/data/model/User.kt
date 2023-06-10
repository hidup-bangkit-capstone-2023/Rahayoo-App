package com.bangkit.rahayoo.data.model

import com.squareup.moshi.Json
import java.time.LocalDate

data class User(
    val uid: String,
    val name: String,
    val email: String,
    val address: String,
    @Json(name = "date_of_birth")
    val dateOfBirth: String,
    val age: Int,
    @Json(name = "department_id")
    val departmentId: Int,
    @Json(name = "avatar_url")
    val avatarUrl: String? = null,
)