package com.linkup.android.feature.auth.signup

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
import com.linkup.android.feature.auth.PasswordValidator
import com.linkup.android.root.NavGroup
import com.linkup.android.ui.components.AuthLogo
import com.linkup.android.ui.components.CustomButton
import com.linkup.android.ui.components.CustomTextField
import com.linkup.android.ui.theme.SubColor

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Composable
fun SignUpScreen(navController: NavController) {
    val viewModel: SignUpViewModel = hiltViewModel()
    val uiState = viewModel.uiState

    var isPwCheckTouched by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var nickName by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    var pwCheck by remember { mutableStateOf("") }

    // error
    val isEmailError by remember {
        derivedStateOf {
            email.isNotEmpty() && !isValidEmail(email)
        }
    }

    val isPwError by remember {
        derivedStateOf {
            pw.isNotEmpty() && !PasswordValidator.isValidPassword(pw)
        }
    }

    val isPwCheckError by remember {
        derivedStateOf {
            isPwCheckTouched &&
                    pwCheck.isNotEmpty() &&
                    !isPwError &&
                    pw != pwCheck
        }
    }


    val isSignUpEnabled =
        email.isNotEmpty() &&
                nickName.isNotEmpty() &&
                pw.isNotEmpty() &&
                pwCheck.isNotEmpty() &&
                !isEmailError &&
                !isPwError &&
                !isPwCheckError &&
                uiState.emailError == null &&
                uiState.nickNameError == null

    val showEmailError = isEmailError || uiState.emailError != null

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navController.navigate(NavGroup.SignIn) {
                popUpTo(NavGroup.SignUp) { inclusive = true }
            }
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
        AuthLogo("회원가입")
        Column(
            modifier = Modifier.padding(top = 64.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CustomTextField(
                value = email,
                onValueChange = {
                    email = it
                    viewModel.clearEmailError()
                }, placeHolder = "이메일을 입력하세요."
            )

            if (showEmailError) {
                Text(
                    text = uiState.emailError ?: "이메일 형식이 맞지 않습니다.",
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }

            CustomTextField(
                value = nickName,
                onValueChange = {
                    nickName = it
                    viewModel.clearNickNameError()
                }, placeHolder = "닉네임을 입력하세요."
            )


            if (uiState.nickNameError != null) {
                Text(
                    text = uiState.nickNameError,
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }


            CustomTextField(
                value = pw,
                onValueChange = {
                    pw = it
                    pwCheck = ""
                    isPwCheckTouched = false
                }, placeHolder = "비밀번호를 입력하세요.",
                isPassword = true
            )

            if (isPwError) {
                Text(
                    text = "비밀번호는 8자 이상의 소문자, 숫자, 특수문자로 이루어져야 합니다.",
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }


            CustomTextField(
                value = pwCheck,
                onValueChange = {
                    pwCheck = it
                    isPwCheckTouched = true
                }, placeHolder = "비밀번호를 다시 입력하세요.",
                isPassword = true
            )

            if (isPwCheckError) {
                Text(
                    text = "비밀번호가 일치하지 않습니다.", color = Color.Red, fontSize = 14.sp
                )
            }

        }

        Column(
            modifier = Modifier.padding(top = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            CustomButton(
                text = "회원가입",
                contentColor = Color.White,
                containerColor = SubColor,
                border = SubColor,
                enabled = isSignUpEnabled,
                onClick = {
                    viewModel.signUp(
                        email = email,
                        username = nickName,
                        password = pw
                    )
                }
            )

            CustomButton(
                text = "로그인",
                contentColor = SubColor,
                containerColor = Color.White,
                border = SubColor,
                onClick = { navController.navigate(NavGroup.SignIn) },
                modifier = Modifier
            )
        }
    }
}