package com.linkup.android.network.auth.signUp

import com.linkup.android.network.BaseResponse
import com.linkup.android.network.LinkUpUrl
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST(LinkUpUrl.signUp)
    suspend fun signUp(@Body request: SignUpRequest): Response<BaseResponse<SignUpResponse>>

}

