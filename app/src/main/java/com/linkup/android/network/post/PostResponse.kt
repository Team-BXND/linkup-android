package com.linkup.android.network.post

import com.linkup.android.network.Category

data class PostComment(
    val commentId: Int,
    val author: String,
    val content: String,
    val isAccepted: Boolean,
    val createdAt: String
)

data class PostDetail(
    val title: String,
    val author: String,
    val category: Category,
    val content: String,
    val like: Int,
    val createdAt: String,
    val isAccepted: Boolean,
    val isLike: Boolean,
    val isAuthor: Boolean,
    val comment: List<PostComment>
)

data class PostDetailResponse(
    val data: PostDetail
)

data class PostResponse (
    val code: String,
    val message: String
)