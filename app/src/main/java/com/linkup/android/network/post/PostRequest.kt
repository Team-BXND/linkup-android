package com.linkup.android.network.post

import com.linkup.android.network.Category

data class PostRequest(
    val category: Category,
    val title: String,
    val content: String,
    val author: String,
)

data class PatchRequest(
    val category: Category,
    val title: String,
    val content: String,
    val author: String
)

data class AnswerRequest(
    val content: String
)

data class AcceptRequest(
    val commentId: Int
)

