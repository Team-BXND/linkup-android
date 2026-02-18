package com.linkup.android.feature.auth.signup

data class SignUpUiState(
    val emailError: String? = null,
    val nickNameError: String? = null,
    val globalError: String? = null
)