package com.linkup.android.network.auth.pwChange

import com.linkup.android.network.BaseResponse
import com.linkup.android.network.LinkUpUrl
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PwChangeService {
    @POST(LinkUpUrl.Auth.send)
    suspend fun send(@Body request: SendRequest): Response<BaseResponse<PwChangeResponse>>

    @POST(LinkUpUrl.Auth.verify)
    suspend fun verify(@Body request: VerifyRequest): Response<BaseResponse<PwChangeResponse>>

    @POST(LinkUpUrl.Auth.change)
    suspend fun pwChange(@Body request: PwChangeRequest): Response<BaseResponse<PwChangeResponse>>
}

