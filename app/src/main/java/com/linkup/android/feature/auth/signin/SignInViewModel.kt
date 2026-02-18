package com.linkup.android.feature.auth.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkup.android.network.ErrorParser
import com.linkup.android.network.auth.signIn.SignInError
import com.linkup.android.network.auth.signIn.SignInRequest
import com.linkup.android.network.auth.signIn.SignInService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInService: SignInService
) : ViewModel() {

    var uiState by mutableStateOf(SignInUiState())
        private set

    fun signIn(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            val result = signInService.signIn(
                SignInRequest(email, password)
            )


            if (result.isSuccessful) {
                val body = result.body()

                if (body != null) {
                    // TODO: accessToken 저장 (DataStore 등)

                    uiState = uiState.copy(
                        isSuccess = true,
                        contentError = null,
                        globalError = null
                    )
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
        }
    }
}