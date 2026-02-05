package com.linkup.android.feature.auth.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkup.android.network.auth.signUp.SignUpRequest
import com.linkup.android.network.auth.signUp.SignUpService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpService: SignUpService
) : ViewModel() {

    var signUpResult by mutableStateOf<String?>(null)
        private set

    fun signUp(
        email: String,
        username: String,
        password: String
    ) {
        viewModelScope.launch {
            try {
                val response = signUpService.signUp(
                    SignUpRequest(
                        email = email,
                        username = username,
                        password = password
                    )
                )

                if (response.isSuccessful) {
                    signUpResult = response.body()?.data?.message
                } else {
                    signUpResult = "회원가입 실패 (${response.code()})"
                }
            } catch (e: Exception) {
                signUpResult = e.message ?: "네트워크 에러"
            }
        }
    }
}
