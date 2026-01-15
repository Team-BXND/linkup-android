package com.linkup.android.feature.profile

import android.widget.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen() {
    Column(Modifier.fillMaxSize()) {
        Card(
            Modifier.size(330.dp, 370.dp)

        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    "프로필",
                    Modifier.padding(bottom = 24.dp)
                )
                InfoItem("닉네임", "임시 더미")
                InfoItem("이메일", "임시 더미")
                InfoItem("닉네임", 1, "위")
                InfoItem("닉네임", 1000, " P", Modifier.padding(bottom = 52.dp))

                Button(
                    {},
                    Modifier.size(110.dp, 35.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color.Blue
                    )
                ) {
                    Text("로그아웃")
                }
            }

        }

    }
}

@Composable
fun InfoItem(title: String, value: String) {
    Column(Modifier.padding(bottom = 8.dp)) {
        Text(title)
        Text(value)

    }
}

@Composable
fun InfoItem(title: String, value: Int, trailing: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(bottom = 8.dp)) {
        Text(title)
        Text("$value$trailing")

    }
}

@Preview
@Composable
fun Preview() {
    ProfileScreen()
}