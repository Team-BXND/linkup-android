package com.linkup.android.network.qna

import com.google.gson.annotations.SerializedName

data class QnaResponse(
    val data: List<QnaItemResponse>,
    val meta: Meta
)

data class QnaItemResponse(
    val id: Int,
    val title: String,
    val author: String,
    val category: Category,
    val like: Int,
    val preview: String,
    val isAccepted: Boolean,
    val commentCount: Int,
    val createdAt: String
)

enum class Category {
    @SerializedName("all") ALL,
    @SerializedName("code") CODE,
    @SerializedName("school") SCHOOL,
    @SerializedName("project") PROJECT
}


data class Meta(
    val total: Int,
    val page: Int,
    val pageSize: Int,
    val totalPages: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean
)
