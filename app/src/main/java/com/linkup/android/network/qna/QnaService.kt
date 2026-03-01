package com.linkup.android.network.qna

import com.linkup.android.network.LinkUpUrl
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QnaService {
    @GET(LinkUpUrl.Popular.HOT)
    suspend fun hot(
        @Query("page") page: Int?
    ): Response<QnaResponse>

    @GET(LinkUpUrl.Post.POST)
    suspend fun popular(
        @Query("page") page: Int?
    ): Response<QnaResponse>
}