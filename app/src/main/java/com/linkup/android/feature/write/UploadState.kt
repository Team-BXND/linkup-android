package com.linkup.android.feature.write

data class UploadState(
    val isUploading: Boolean = false,
    val uploadedUrl: String? = null,
    val error: String? = null,
    val s3Key: String? = ""
)