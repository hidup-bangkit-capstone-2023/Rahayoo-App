package com.bangkit.rahayoo.data.model.response

import com.squareup.moshi.Json

data class MessageResponse(
    val message: String,
    @Json(name = "user_id")
    val userId: String
)