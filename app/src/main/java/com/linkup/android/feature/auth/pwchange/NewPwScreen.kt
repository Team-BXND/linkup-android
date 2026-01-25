package com.linkup.android.feature.auth.pwchange

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.linkup.android.feature.auth.PasswordValidator
import com.linkup.android.root.NavGroup
import com.linkup.android.ui.components.AuthLogo
import com.linkup.android.ui.components.CustomButton
import com.linkup.android.ui.components.CustomTextField
import com.linkup.android.ui.theme.SubColor

@Composable
fun NewPwScreen(navController: NavController) {

    var isPwCheckTouched by remember { mutableStateOf(false) }

    var pw by remember { mutableStateOf("") }
    var pwCheck by remember { mutableStateOf("") }

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

    val isButtonEnabled =
        pw.isNotEmpty() &&
                pwCheck.isNotEmpty() &&
                !isPwError &&
                !isPwCheckError

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 32.5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthLogo(text = "비밀번호 찾기")

        Column(
            modifier = Modifier
                .padding(top = 64.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CustomTextField(
                value = pw,
                onValueChange = {
                    pw = it
                    pwCheck = ""
                    isPwCheckTouched = false
                }, placeHolder = "새 비밀번호를 입력하세요."
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
                }, placeHolder = "새 비밀번호를 다시 입력하세요."
            )

            if (isPwCheckError) {
                Text(
                    text = "비밀번호가 일치하지 않습니다.", color = Color.Red, fontSize = 14.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(top = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            CustomButton(
                text = "비밀번호 변경",
                contentColor = Color.White,
                containerColor = SubColor,
                border = SubColor,
                enabled = isButtonEnabled,
                onClick = {
                    navController.navigate(NavGroup.SignIn)
                }
            )
        }

    }
}