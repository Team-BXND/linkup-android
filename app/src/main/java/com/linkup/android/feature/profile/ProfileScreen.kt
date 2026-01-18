package com.linkup.android.feature.profile

import android.R
import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.linkup.android.ui.theme.MainColor
import com.linkup.android.ui.theme.SubColor

@Composable
fun ProfileScreen() {
    Column(
        Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
           modifier = Modifier
               .size(330.dp, 420.dp)
               .padding(bottom = 20.dp)
               .shadow(elevation = 3.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = "프로필",
                    modifier = Modifier.padding(bottom = 24.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,

                )
                InfoItem("닉네임", "임시 더미")

                InfoItem("이메일", "임시 더미")

                InfoItem("닉네임", 1, "위")

                InfoItem("닉네임", 1000, " P",)

                Row(
                    Modifier.padding(top = 32.dp),

                    
                ) {
                    Spacer(Modifier.weight(1f))

                    Button(
                        {},
                        shape= RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MainColor
                        )
                    ) {
                        Text("로그아웃")
                    }
                }
            }
        }
        Button(
            {},
            Modifier.padding(bottom = 20.dp).size(330.dp,55.dp)
                .shadow(elevation = 3.dp)
                ,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row() {
                Text("내 질문")
                Spacer(Modifier.weight(1f))
            }
        }

        Button(
            {},
            Modifier.size(330.dp,55.dp).shadow(elevation = 3.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row() {
                Text("내 질문")
                Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun InfoItem(title: String, value: String) {
    Column(Modifier.padding(bottom = 8.dp)) {
        Text(title, fontWeight = FontWeight.Medium, fontSize = 18.sp, color = Color.Gray)
        Text(value, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)

    }
}

@Composable
fun InfoItem(title: String, value: Int, trailing: String) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        Text(title, fontWeight = FontWeight.Medium, fontSize = 18.sp, color = Color.Gray)
        Text(
            "$value$trailing",
            color = SubColor,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )

    }
}

@Preview
@Composable
fun Preview() {
    ProfileScreen()
}