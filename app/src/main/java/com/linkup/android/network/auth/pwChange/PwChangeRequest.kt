package com.linkup.android.network.auth.pwChange

data class SendRequest (
    val email: String
)

data class VerifyRequest (
    val email: String,
    val code: Int
)

data class PwChangeRequest (
    val email: String,
    val password: String
)