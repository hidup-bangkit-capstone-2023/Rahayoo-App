package com.bangkit.rahayoo.data.model.response

import com.squareup.moshi.Json

data class EmployeeId(
    @Json(name = "employee_id")
    val employeeId: String
)
