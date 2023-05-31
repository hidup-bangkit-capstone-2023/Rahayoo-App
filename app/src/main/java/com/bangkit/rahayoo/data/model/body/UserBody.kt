package com.bangkit.rahayoo.data.model.body

import com.squareup.moshi.Json

data class UserBody(
    val name: String,
    val address: String,
    @Json(name = "date_of_birth")
    val dateOfBirth: String,
    val age: Int,
    @Json(name = "job_title")
    val jobTitle: String,
    val department: String,
    val email: String,
)