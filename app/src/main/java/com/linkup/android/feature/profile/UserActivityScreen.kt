package com.linkup.android.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.linkup.android.R
import com.linkup.android.network.profile.MyAnswer
import com.linkup.android.network.profile.MyQuestion
import com.linkup.android.ui.theme.SubColor

enum class ActivityType(val rawValue: String) {
    ANSWER("내 답변"),
    QUESTION("내 질문")
}

@Composable
fun UserActivityScreen(activityType: ActivityType, viewModel: ProfileViewModel = hiltViewModel()) {

    LaunchedEffect(activityType) {
        when (activityType) {
            ActivityType.ANSWER -> viewModel.getMyAnswers(initialFetch = true)
            ActivityType.QUESTION -> viewModel.getMyQuestions(initialFetch = true)
        }
    }

    val state = viewModel.state.value

    Card(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(activityType.rawValue, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 20.dp))
            LazyColumn {
                if (activityType == ActivityType.ANSWER) {
                    itemsIndexed(state.myAnswers) { index, answer ->
                        ProfileItem(activityType = activityType, answer = answer)
                        if (index == state.myAnswers.lastIndex && state.hasNextAnswers && !state.isLoading) {
                            LaunchedEffect(Unit) {
                                viewModel.getMyAnswers()
                            }
                        }
                    }
                } else {
                    itemsIndexed(state.myQuestions) { index, question ->
                        ProfileItem(activityType = activityType, question = question)
                        if (index == state.myQuestions.lastIndex && state.hasNextQuestions && !state.isLoading) {
                            LaunchedEffect(Unit) {
                                viewModel.getMyQuestions()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileItem(activityType: ActivityType, answer: MyAnswer? = null, question: MyQuestion? = null) {
    Card(
        modifier = Modifier.fillMaxWidth().height(60.dp).padding(bottom = 8.dp),
        shape = RoundedCornerShape(12.dp),
        border = CardDefaults.outlinedCardBorder(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CategoryChip(category = answer?.category ?: question?.category ?: "")
                    Text(
                        text = if (activityType == ActivityType.ANSWER) answer?.answer ?: "" else question?.title ?: "",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                if (activityType == ActivityType.ANSWER) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Icon for the question title if available
                        Text(answer?.title ?: "", fontSize = 14.sp, color = Color.Gray)
                    }
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(id = R.drawable.like_icon), contentDescription = "like")
                        Text("유용해요 ${question?.like ?: 0}", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(end = 16.dp))
                        Image(painter = painterResource(id = R.drawable.comment_icon), contentDescription = "answer")
                        Text("답변 수 ${question?.commentCount ?: 0}", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryChip(category: String) {
    Card(
        modifier = Modifier.padding(end = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SubColor.copy(alpha = 0.1f))
    ) {
        Text(
            text = category,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            fontSize = 14.sp,
            color = SubColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserActivityScreenPreview() {
    UserActivityScreen(activityType = ActivityType.QUESTION)
}
