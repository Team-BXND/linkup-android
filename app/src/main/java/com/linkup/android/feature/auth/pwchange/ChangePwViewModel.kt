package com.linkup.android.feature.auth.pwchange

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkup.android.network.ErrorParser
import com.linkup.android.network.auth.pwChange.PwChangeRequest
import com.linkup.android.network.auth.pwChange.PwChangeResponse
import com.linkup.android.network.auth.pwChange.PwChangeService
import com.linkup.android.network.auth.pwChange.SendRequest
import com.linkup.android.network.auth.pwChange.VerifyRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePwViewModel @Inject constructor(
    private val pwChangeService: PwChangeService,
) : ViewModel() {

    var uiState by mutableStateOf(ChangePwState())
        private set


    fun sendEmail(email: String) {
        viewModelScope.launch {

            uiState = uiState.copy(
                isLoading = true,
                emailError = null,
                codeError = null,
                globalError = null,
                step = ChangePwStep.NONE
            )

            try {
                val result = pwChangeService.send(SendRequest(email))

                if (result.isSuccessful) {
                    uiState = uiState.copy(step = ChangePwStep.EMAIL_SENT)

                } else {
                    val error = ErrorParser.parse(result, PwChangeResponse::class.java)

                    uiState = when (error?.code) {
                        "INVALID_EMAIL" -> uiState.copy(emailError = "잘못된 이메일입니다.")

                        else -> uiState.copy(globalError = error?.message ?: "알 수 없는 오류입니다.")
                    }
                }

            } catch (e: Exception) {
                uiState = uiState.copy(
                    globalError = "네트워크 오류가 발생했습니다."
                )
            }

            uiState = uiState.copy(isLoading = false)
        }
    }


    fun verifyCode(email: String, code: Int) {

        viewModelScope.launch {

            uiState = uiState.copy(
                isLoading = true,
                emailError = null,
                codeError = null,
                globalError = null
            )

            try {
                val result = pwChangeService.verify(
                    VerifyRequest(email, code)
                )
                if (result.isSuccessful) {
                    uiState = uiState.copy(step = ChangePwStep.CODE_VERIFIED)
                } else {
                    val error = ErrorParser.parse(result, PwChangeResponse::class.java)

                    uiState = when (error?.code) {
                        "INVALID_VERIFICATION_CODE" -> {
                            uiState.copy(codeError = "인증번호가 올바르지 않습니다.")
                        }

                        else -> {
                            uiState.copy(globalError = "ㄹ")
                        }
                    }
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    globalError = "네트워크 오류가 발생했습니다."
                )
            }

            uiState = uiState.copy(isLoading = false)

        }
    }

    fun changePassword(email: String, password: String) {

        viewModelScope.launch {

            uiState = uiState.copy(
                isLoading = true,
                emailError = null,
                codeError = null,
                globalError = null
            )

            try {
                val result = pwChangeService.pwChange(
                    PwChangeRequest(email, password)
                )
                Log.d("Test","${email}, ${password}")
                if (result.isSuccessful) {
                    uiState = uiState.copy(step = ChangePwStep.PASSWORD_CHANGED)
                } else {
                    val error = ErrorParser.parse(result, PwChangeResponse::class.java)

                    uiState = when (error?.code) {
                        "EMAIL_ALREADY_USED" -> {
                            uiState.copy(emailError = "잘못된 이메일입니다.")
                        }

                        else -> {
                            uiState.copy(globalError = "ㄹ")
                        }
                    }
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    globalError = "네트워크 오류가 발생했습니다."
                )
            }

            uiState = uiState.copy(isLoading = false)
        }
    }

    fun resetStep() {
        uiState = uiState.copy(step = ChangePwStep.NONE)
    }


}