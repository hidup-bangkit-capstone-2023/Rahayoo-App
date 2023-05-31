package com.bangkit.rahayoo.data.remote

import com.bangkit.rahayoo.data.model.StressTestQuestion
import com.bangkit.rahayoo.data.model.body.RegisterBody
import com.bangkit.rahayoo.data.model.body.UserBody
import com.bangkit.rahayoo.data.model.response.CompanyResponse
import com.bangkit.rahayoo.data.model.response.MessageResponse
import com.squareup.moshi.Json
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth/register")
    suspend fun register(
        @Header("Authorization") authorization: String,
        @Body body: RegisterBody
    ): Response<MessageResponse>

    @POST("api/auth/user-update")
    suspend fun updateUser(
        @Header("Authorization") authorization: String,
        @Body body: UserBody
    ): Response<MessageResponse>

    @POST("api/auth/company")
    suspend fun addCompany(
        @Header("Authorization") authorization: String,
        @Body
        @Json(name = "company_code")
        companyCode: String
    ): Response<CompanyResponse>

    @POST("api/stress/")
    suspend fun submitStressTestAnswer(
        @Header("Authorization") authorization: String,
        @Body body: List<StressTestQuestion>
    ): Response<MessageResponse>
}