package com.linkup.android.feature.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.linkup.android.network.profile.Categroy
import com.linkup.android.ui.theme.SubColor
import com.linkup.android.R

@Composable
fun UserActivityScreen() {
    val dummylist = listOf<String>("1", "2", "3")

    Column(
        Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape= RoundedCornerShape(16.dp),
            modifier = Modifier.size(330.dp, 655.dp)
                .shadow(elevation = 3.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )


        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    "내 질문",
                    Modifier.padding(bottom = 20.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                for (item in dummylist) {
                    QuestionItem(title = item)

                }
            }
        }
    }
}
@Composable
fun QuestionItem(title: String = "", like: Int = 1, commentCount: Int = 2, categroy: Categroy = Categroy.project) {

    Card(
        Modifier.padding(bottom = 16.dp)
            .size(300.dp, 60.dp),
        border = BorderStroke(1.dp,Color.Gray.copy(alpha = 0.25f)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )




    ) {
        Column(
            Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row() {
                Card(
                    Modifier.size(75.dp, 27.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = SubColor.copy(alpha = 0.1f),
                        contentColor = SubColor)

                ) {
                    Text(categroy.value, Modifier.fillMaxWidth().padding(top = 3.dp), textAlign = TextAlign.Center,)
                }
                Text(
                    title,
                    Modifier.padding(start = 12.dp)
                )
            }
            Row() {
                //이미지
                Icon(
                    painter = painterResource(R.drawable.ans),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.padding(end = 3.dp).size(10.dp, 17.dp)
                )
                Text(
                    text = "유용해요 $like",
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(end = 16.dp),
                    color = Color.Gray
                )
                Icon(
                    painter = painterResource(R.drawable.good),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.padding(end = 3.dp).size(10.dp, 17.dp)
                )
                Text(
                    text = "유용해요 $commentCount",
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(end = 16.dp),
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun AnswerItem() {

}

@Preview
@Composable
fun ActivityPreview() {
    UserActivityScreen()
}