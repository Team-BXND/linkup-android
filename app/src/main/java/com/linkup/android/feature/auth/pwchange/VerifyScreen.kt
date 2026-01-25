package com.linkup.android.feature.auth.pwchange

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.linkup.android.ui.components.CustomButton
import com.linkup.android.ui.components.CustomTextField
import com.linkup.android.ui.theme.SubColor

@Composable
fun VerifyScreen(navController: NavController) {

    var verify by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 32.5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(84.dp)
            )
            Text(
                text = "비밀번호 찾기",
                fontSize = 40.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 64.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CustomTextField(
                value = verify,
                onValueChange = {
                    verify = it
                }, placeHolder = "인증번호를 입력하세요."
            )
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
                onClick = {
                    navController.navigate(NavGroup.NewPw)
                }
            )
        }

    }
}