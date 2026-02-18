package com.linkup.android.feature.auth.signin

data class SignInUiState (
    val isSuccess: Boolean = false,
    val contentError: String? = null,
    val globalError: String? = null
)