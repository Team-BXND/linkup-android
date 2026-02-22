package com.linkup.android.feature.auth.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkup.android.network.ErrorParser
import com.linkup.android.network.auth.signUp.SignUpRequest
import com.linkup.android.network.auth.signUp.SignUpResponse
import com.linkup.android.network.auth.signUp.SignUpService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpService: SignUpService
) : ViewModel() {

    var uiState by mutableStateOf(SignUpUiState())
        private set

    fun signUp(email: String, username: String, password: String) {
        viewModelScope.launch {
            val result = signUpService.signUp(
                SignUpRequest(email, username, password)
            )

            if (result.isSuccessful) {
                uiState = uiState.copy(isSuccess = true)
            } else {
                val error = ErrorParser.parse(result, SignUpResponse::class.java)

                uiState = when (error?.code) {
                    "EMAIL_ALREADY_USED" -> {
                        uiState.copy(emailError = "이미 사용 중인 이메일입니다.")
                    }

                    "USERNAME_ALREADY_USED" -> {
                        uiState.copy(nickNameError = "이미 사용 중인 닉네임입니다.")
                    }

                    else -> {
                        uiState.copy(globalError = "회원가입에 실패했습니다.")
                    }
                }
            }
        }
    }
    fun clearEmailError() {
        uiState = uiState.copy(emailError = null)
    }

    fun clearNickNameError() {
        uiState = uiState.copy(nickNameError = null)
    }

}
