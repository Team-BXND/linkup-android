package com.linkup.android.feature.auth.pwchange

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
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
import com.linkup.android.root.NavGroup
import com.linkup.android.ui.components.AuthLogo
import com.linkup.android.ui.components.CustomButton
import com.linkup.android.ui.components.CustomTextField
import com.linkup.android.ui.theme.SubColor

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Composable
fun PwChangeScreen(navController: NavController) {

    val viewModel: ChangePwViewModel = hiltViewModel()
    val uiState = viewModel.uiState

    var email by remember { mutableStateOf("") }

    val isEmailError by remember {
        derivedStateOf {
            email.isNotEmpty() && !isValidEmail(email)
        }
    }

    LaunchedEffect(uiState.step) {
        if (uiState.step == ChangePwStep.EMAIL_SENT) {
            navController.navigate("verify/$email")
            viewModel.resetStep()
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
            modifier = Modifier.padding(top = 64.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CustomTextField(
                value = email, onValueChange = {
                    email = it
                }, placeHolder = "이메일을 입력하세요."
            )

            if (uiState.emailError != null || isEmailError) {
                Text(
                    text = uiState.emailError ?: "이메일 형식이 맞지 않습니다.",
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }
        }

        Column(
            modifier = Modifier.padding(top = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            CustomButton(
                text = "인증번호 발송",
                contentColor = Color.White,
                containerColor = SubColor,
                border = SubColor,
                enabled = email.isNotEmpty() && !isEmailError && !uiState.isLoading,
                        onClick = {
                    viewModel.sendEmail(email)
                })

            CustomButton(
                text = "로그인 하기",
                contentColor = SubColor,
                containerColor = Color.White,
                border = SubColor,
                onClick = { navController.navigate(NavGroup.SignIn) },
                modifier = Modifier
            )
        }

    }
}