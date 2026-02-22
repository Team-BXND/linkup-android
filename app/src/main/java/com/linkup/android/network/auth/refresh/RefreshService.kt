package com.linkup.android.network.auth.refresh

import com.linkup.android.network.BaseResponse
import com.linkup.android.network.LinkUpUrl
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshService {
    @POST(LinkUpUrl.Auth.refresh)
    suspend fun refresh(@Body request: RefreshRequest): Response<BaseResponse<RefreshResponse>>
}