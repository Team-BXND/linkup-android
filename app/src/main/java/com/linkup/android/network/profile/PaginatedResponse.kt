package com.linkup.android.network.profile

data class PaginatedResponse<T>(
    val data: List<T>,
    val meta: Meta
)
