package com.linkup.android.feature.profile

import android.graphics.fonts.Font
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.linkup.android.root.NavGroup
import com.linkup.android.ui.components.CustomButton
import com.linkup.android.ui.components.TopBar
import com.linkup.android.ui.theme.SubColor

@Composable
fun MoveToAuthScreen(navController: NavController) {
    Column(
        Modifier.fillMaxSize().padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        TopBar(navController = navController)
        Text(
            "로그인이 필요합니다",
            Modifier.padding(top = 200.dp).padding(bottom = 8.dp),
            fontWeight = FontWeight.SemiBold, fontSize = 32.sp
        )
        Text(
            "로그인을 하고 더 많은 기능을 이용하세요",
            Modifier.padding(bottom = 60.dp),
            fontWeight = FontWeight.SemiBold, fontSize = 20.sp
        )
        CustomButton(
            text = "로그인 하기",
            contentColor = Color.White,
            containerColor = SubColor,
            border = SubColor,
            modifier = Modifier.padding(bottom = 16.dp),
            onClick = {
                navController.navigate(NavGroup.SIGNIN)
            }
        )

        CustomButton(
            text = "회원가입 하기",
            contentColor = SubColor,
            containerColor = Color.White,
            border = SubColor,
            onClick = { navController.navigate(NavGroup.SIGNUP) },
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

