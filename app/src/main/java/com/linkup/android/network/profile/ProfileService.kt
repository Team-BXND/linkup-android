package com.linkup.android.network.profile

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileService {
    @GET("profile")
    suspend fun getProfile(): Response<ApiResponse<UserProfile>>

    @GET("profile/myans")
    suspend fun getMyAnswers(@Query("page") page: Int): Response<PaginatedResponse<MyAnswer>>

    @GET("profile/myque")
    suspend fun getMyQuestions(@Query("page") page: Int): Response<PaginatedResponse<MyQuestion>>
}
