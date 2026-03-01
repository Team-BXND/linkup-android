package com.linkup.android.network.profile
// 기본 응답 구조
data class ApiResponse<T>(
    val data: T,
    val meta: MetaData? = null
)

data class MetaData(
    val total: Int,
    val page: Int,
    val pageSize: Int,
    val totalPages: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean
)

// 프로필 정보
data class UserProfile(
    val username: String = "",
    val email: String = "",
    val point: Int = 0,
    val ranking: Int = 0
)

// 답변 내역 아이템
data class MyAnswer(
    val id: Int,
    val title: String,
    val preview: String,
    val category: String,
    val answer: String
)

// 질문 내역 아이템
data class MyQuestion(
    val id: Int,
    val title: String,
    val preview: String,
    val category: String,
    val like: Int,
    val commentCount: Int,
    val isAccepted: Boolean
)