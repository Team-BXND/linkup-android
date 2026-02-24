package com.linkup.android.feature.auth.pwchange

data class ChangePwState(
    val emailError: String? = null,
    val codeError: String? = null,
    val globalError: String? = null,
    val step: ChangePwStep = ChangePwStep.NONE,
    val isLoading: Boolean = false,
)

enum class ChangePwStep {
    NONE,
    EMAIL_SENT,
    CODE_VERIFIED,
    PASSWORD_CHANGED
}