package com.bangkit.rahayoo.data.model

import com.squareup.moshi.Json
import java.time.LocalDate

data class User(
    @Json(name = "employee_id")
    val uid: String,
    val name: String,
    val email: String,
    val address: String? = "",
    @Json(name = "date_of_birth")
    val dateOfBirth: String? = "",
    val age: Int? = 0,
    @Json(name = "department_id")
    val departmentId: String? = "",
    @Json(name = "job_title")
    val jobTitle: String? = "",
    @Json(name = "avatar_url")
    val avatarUrl: String? = null,
)