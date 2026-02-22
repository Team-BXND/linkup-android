package com.linkup.android.network.auth.signIn

import com.linkup.android.network.BaseResponse
import com.linkup.android.network.LinkUpUrl
import com.linkup.android.network.auth.SignIn.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInService {
    @POST(LinkUpUrl.Auth.signIn)
    suspend fun signIn(@Body request: SignInRequest): Response<BaseResponse<SignInResponse>>
}