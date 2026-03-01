package com.linkup.android.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.linkup.android.R
import com.linkup.android.feature.auth.AuthViewModel
import com.linkup.android.root.NavGroup
import com.linkup.android.ui.components.TopBar
import com.linkup.android.ui.theme.MainColor
import com.linkup.android.ui.theme.SubColor

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = hiltViewModel(), authViewModel: AuthViewModel = hiltViewModel()) {
    val state = viewModel.state.value

    LaunchedEffect(Unit) {
        viewModel.getProfile()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TopBar(navController)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("프로필", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 24.dp))
                state.userProfile?.let {
                    InfoItem("닉네임", it.username)
                    InfoItem("이메일", it.email)
                    InfoItem("답변자 순위", "${it.ranking}위")
                    InfoItem("포인트", "${it.point} P")
                }
                Spacer(modifier = Modifier.height(32.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(
                        onClick = {
                            authViewModel.logout()
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SubColor),
                        modifier = Modifier.width(110.dp).height(35.dp)
                    ) {
                        Text("로그아웃", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        ActivityButton(text = "내 답변") {
            navController.navigate(NavGroup.UserActivity.createRoute(ActivityType.ANSWER))
        }

        Spacer(modifier = Modifier.height(20.dp))

        ActivityButton(text = "내 질문") {
            navController.navigate(NavGroup.UserActivity.createRoute(ActivityType.QUESTION))
        }
    }
}

@Composable
private fun InfoItem(title: String, value: String) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        Text(title, fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Color.Gray)
        Text(value, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = if (title == "포인트" || title == "답변자 순위") MainColor else Color.Black)
    }
}

@Composable
private fun ActivityButton(text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.weight(1f))
            Image(painter = painterResource(id = R.drawable.arrow_icon), contentDescription = "arrow")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    // In a real app, you'd pass a fake NavController
    // ProfileScreen(navController = NavController(LocalContext.current))
}
