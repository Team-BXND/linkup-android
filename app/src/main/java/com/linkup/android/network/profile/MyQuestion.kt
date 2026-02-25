package com.linkup.android.network.profile

data class MyQuestion(
    val id: Int,
    val title: String,
    val preview: String,
    val category: String,
    val like: Int,
    val commentCount: Int,
    val isAccepted: Boolean,
    val page: Int
)
