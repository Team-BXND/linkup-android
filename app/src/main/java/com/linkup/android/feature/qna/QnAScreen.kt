package com.linkup.android.feature.qna

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.linkup.android.R
import com.linkup.android.ui.components.TopBar

@Composable
fun QnaScreen(navController: NavController) {
    Column(
    ) {
        TopBar(navController)

        Column(
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text(
                "💬대소고에서 궁금한 점이 있다면?"
            )

            Image(
                painter = painterResource(R.drawable.code),
                contentDescription = "코드 게시판"
            )
            Image(
                painter = painterResource(R.drawable.school),
                contentDescription = "학교 생활 게시판"
            )
            Image(
                painter = painterResource(R.drawable.project),
                contentDescription = "프로젝트 게시판"
            )
        }
    }

}