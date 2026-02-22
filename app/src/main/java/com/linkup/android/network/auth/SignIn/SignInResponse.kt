package com.linkup.android.network.auth.signIn

data class SignInResponse (
    val accessToken: String,
    val refreshToken: String,
)

data class SignInError (
    val code: String,
    val message: String
)