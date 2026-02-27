package com.linkup.android.feature.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.linkup.android.root.NavGroup
import com.linkup.android.ui.components.CustomButton
import com.linkup.android.ui.components.TopBar
import com.linkup.android.ui.theme.SubColor

@Composable
fun MoveToAuthScreen(navController: NavController) {
    Column() {
        TopBar(navController = navController)
        Text("로그인이 필요합니다")
        Text("로그인을 하고 더 많은 기능을 이용하세요")
        CustomButton(
            text = "로그인 하기",
            contentColor = Color.White,
            containerColor = SubColor,
            border = SubColor,
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
            modifier = Modifier
        )
    }
}

