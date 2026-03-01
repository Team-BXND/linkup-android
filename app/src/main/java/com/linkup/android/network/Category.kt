package com.linkup.android.network

import com.google.gson.annotations.SerializedName

enum class Category(val label: String) {
    @SerializedName("all") ALL("전체"),
    @SerializedName("school") SCHOOL("학교생활"),
    @SerializedName("code") CODE("코드"),
    @SerializedName("project") PROJECT("프로젝트")
}