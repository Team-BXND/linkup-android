package com.linkup.android.network.auth.signUp

data class BaseResponse<T>(
    val data: T
)

data class SignUpMessage(
    val message: String
)