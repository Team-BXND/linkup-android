package com.linkup.android.network.auth.signIn

import com.linkup.android.network.BaseResponse
import com.linkup.android.network.LinkUpUrl
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInService {
    @POST(LinkUpUrl.signIn)
    suspend fun signIn(@Body request: SignInRequest): Response<BaseResponse<SignInResponse>>
}