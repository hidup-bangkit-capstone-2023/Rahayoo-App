package com.bangkit.rahayoo.data.model.body

import com.squareup.moshi.Json

data class UserIdBody(
    @Json(name = "user_id")
    val userId: String
)
