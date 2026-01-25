package com.linkup.android.feature.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.linkup.android.R
import com.linkup.android.root.NavGroup
import com.linkup.android.ui.components.AuthLogo
import com.linkup.android.ui.components.CustomButton
import com.linkup.android.ui.components.CustomTextField
import com.linkup.android.ui.theme.SubColor

@Composable
fun SignInScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    var isLoginError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 32.5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthLogo("로그인")

        Column(
            modifier = Modifier
                .padding(top = 64.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CustomTextField(
                value = email,
                onValueChange = { email = it },
                placeHolder = "이메일을 입력하세요."

            )

            CustomTextField(
                value = pw,
                onValueChange = { pw = it },
                placeHolder = "비밀번호를 입력하세요."

            )

            if (isLoginError) {
                Text(
                    text = "이메일 또는 비밀번호가 일치하지 않습니다.",
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }

            Text(
                text = "비밀번호를 잊으셨나요?",
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .clickable {
                        navController.navigate(NavGroup.PwChange)
                    }
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            CustomButton(
                text = "로그인 하기",
                contentColor = Color.White,
                containerColor = SubColor,
                border = SubColor,
                onClick = {
                    if (email.isBlank() || pw.isBlank()) {
                        isLoginError = true
                    } else {
                        isLoginError = false
                        // 실제 로그인 로직
                    }
                }
            )

            CustomButton(
                text = "회원가입 하기",
                contentColor = SubColor,
                containerColor = Color.White,
                border = SubColor,
                onClick = { navController.navigate(NavGroup.SignUp) },
                modifier = Modifier
            )
        }
    }
}