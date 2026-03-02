package com.linkup.android.feature.write

data class WriteUiState(
    val globalError: String? = null,
    val isSuccess: Boolean = false,
    val s3Key: String? = null
)