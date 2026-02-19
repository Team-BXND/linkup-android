package com.linkup.android.network.auth.refresh

data class RefreshResponse (
    val accessToken: String,
    val refreshToken: String,
)