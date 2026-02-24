package com.linkup.android.network

data class BaseResponse<T>(
    val status: Int,
    val data: T
)