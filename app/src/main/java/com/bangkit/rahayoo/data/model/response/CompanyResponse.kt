package com.bangkit.rahayoo.data.model.response

import com.squareup.moshi.Json

data class CompanyResponse(
    @Json(name = "company_id")
    val companyId: String,
    @Json(name = "company_name")
    val companyName: String,
    @Json(name = "company_code")
    val companyCode: String,
    val message: String,
)
