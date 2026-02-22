package com.linkup.android.feature.auth.pwchange

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.linkup.android.ui.components.AuthLogo
import com.linkup.android.ui.components.CustomButton
import com.linkup.android.ui.components.CustomTextField
import com.linkup.android.ui.theme.SubColor

@Composable
fun VerifyScreen(
    navController: NavController,
    email: String
) {

    val viewModel: ChangePwViewModel = hiltViewModel()
    val uiState = viewModel.uiState

    var code by remember { mutableStateOf("") }

    val isValidCode = code.length == 6 && code.all { it.isDigit() }

    LaunchedEffect(uiState.step) {
        if (uiState.step == ChangePwStep.CODE_VERIFIED) {
            navController.navigate("changePw/${email}")
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 32.5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthLogo("비밀번호 찾기")

        Column(
            modifier = Modifier
                .padding(top = 64.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CustomTextField(
                value = code,
                onValueChange = {
                    code = it
                }, placeHolder = "인증번호를 입력하세요."
            )

            if (uiState.codeError != null) {
                Text(
                    text = uiState.codeError,
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(top = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            CustomButton(
                text = "인증번호 확인",
                contentColor = Color.White,
                containerColor = SubColor,
                border = SubColor,
                enabled = isValidCode && !uiState.isLoading,
                onClick = {
                    val code = code.toIntOrNull()
                    if (code != null) {
                        Log.d("Test","${email}")
                        viewModel.verifyCode(email, code)
                    }
                }

            )
        }

    }
}