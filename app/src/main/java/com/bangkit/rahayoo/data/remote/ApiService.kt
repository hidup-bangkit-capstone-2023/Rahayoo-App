package com.bangkit.rahayoo.data.remote

import com.bangkit.rahayoo.data.model.StressTestAnswer
import com.bangkit.rahayoo.data.model.User
import com.bangkit.rahayoo.data.model.body.RegisterBody
import com.bangkit.rahayoo.data.model.body.UserBody
import com.bangkit.rahayoo.data.model.response.CompanyResponse
import com.bangkit.rahayoo.data.model.response.MessageResponse
import com.bangkit.rahayoo.data.model.response.MessageResponseWithUserId
import com.bangkit.rahayoo.data.model.response.StressLevelResponse
import com.bangkit.rahayoo.data.model.response.WeeklyStatusResponse
import com.squareup.moshi.Json
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("api/auth/register")
    suspend fun register(
        @Header("Authorization") authorization: String,
        @Body body: RegisterBody
    ): Response<MessageResponseWithUserId>

    @POST("api/auth/user-update/{id}")
    suspend fun updateUserData(
        @Header("Authorization") authorization: String,
        @Body userBody: UserBody,
        @Path("id") userId: String
    ): Response<MessageResponse>

    @GET("api/mood/weekly-calendar/{id}")
    suspend fun getUserWeeklyCalendar(
        @Header("Authorization") authorization: String,
        @Path("id") userId: String
    ): Response<WeeklyStatusResponse>

    @GET("api/stress-level/weekly/{id}")
    suspend fun getWeeklyStressLevel(
        @Header("Authorization") authorization: String,
        @Path("id") userId: String
    ): Response<StressLevelResponse>

    @POST("api/auth/company")
    suspend fun addCompany(
        @Header("Authorization") authorization: String,
        @Body
        @Json(name = "company_code")
        companyCode: String
    ): Response<CompanyResponse>

    @POST("api/stress-level/add/{id}")
    suspend fun submitStressTestAnswer(
        @Header("Authorization") authorization: String,
        @Body body: StressTestAnswer,
        @Path("id") userId: String
    ): Response<MessageResponse>

    @GET("api/auth/employee/data/{id}")
    suspend fun getUserData(
        @Header("Authorization") authorization: String, @Path("id") userId: String
    ): Response<User>
}