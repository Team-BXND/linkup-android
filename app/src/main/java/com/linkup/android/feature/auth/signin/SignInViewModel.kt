package com.linkup.android.feature.auth.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkup.android.data.datastore.UserRepository
import com.linkup.android.network.ErrorParser
import com.linkup.android.network.auth.signIn.SignInError
import com.linkup.android.network.auth.signIn.SignInRequest
import com.linkup.android.network.auth.signIn.SignInService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInService: SignInService,
    private val userRepository: UserRepository
) : ViewModel() {

    var uiState by mutableStateOf(SignInUiState())
        private set

    fun signIn(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            try {
                val result = signInService.signIn(
                    SignInRequest(email, password)
                )

                if (result.isSuccessful) {
                    val body = result.body()

                    if (body != null && body.data != null) {
                        userRepository.saveToken(
                            accessToken = body.data.accessToken,
                            refreshToken = body.data.refreshToken
                        )

                        uiState = uiState.copy(
                            isSuccess = true,
                            contentError = null,
                            globalError = null
                        )
                    } else {
                        uiState = uiState.copy(globalError = "응답 데이터가 올바르지 않습니다.")
                    }
                } else {
                    val error = ErrorParser.parse(result, SignInError::class.java)

                    uiState = when (error?.code) {
                        "INVALID_CREDENTIALS" -> {
                            uiState.copy(contentError = "이메일 또는 비밀번호가 올바르지 않습니다.")
                        }

                        else -> {
                            uiState.copy(globalError = "로그인에 실패했습니다.")
                        }
                    }
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    globalError = "네트워크 오류가 발생했습니다: ${e.message}"
                )
            }
        }
    }

    fun resetSuccessState() {
        uiState = uiState.copy(isSuccess = false)
    }

    fun setError(message: String) {
        uiState = uiState.copy(globalError = message)
    }
}