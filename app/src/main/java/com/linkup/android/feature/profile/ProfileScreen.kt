package com.linkup.android.feature.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    val state = viewModel.state.value
    var selectedTabIndex by remember { mutableStateOf(0) }

    LaunchedEffect(selectedTabIndex) {
        when (selectedTabIndex) {
            0 -> viewModel.getProfile()
            1 -> viewModel.getMyAnswers()
            2 -> viewModel.getMyQuestions()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            Tab(selected = selectedTabIndex == 0, onClick = { selectedTabIndex = 0 }) {
                Text("My Info", modifier = Modifier.padding(16.dp))
            }
            Tab(selected = selectedTabIndex == 1, onClick = { selectedTabIndex = 1 }) {
                Text("My Answers", modifier = Modifier.padding(16.dp))
            }
            Tab(selected = selectedTabIndex == 2, onClick = { selectedTabIndex = 2 }) {
                Text("My Questions", modifier = Modifier.padding(16.dp))
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.error != null) {
                Text(
                    text = state.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                when (selectedTabIndex) {
                    0 -> {
                        if (state.userProfile != null) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = "Username: ${state.userProfile.username}")
                                Text(text = "Email: ${state.userProfile.email}")
                                Text(text = "Points: ${state.userProfile.point}")
                                Text(text = "Ranking: ${state.userProfile.ranking}")
                            }
                        }
                    }
                    1 -> {
                        LazyColumn(modifier = Modifier.padding(16.dp)) {
                            items(state.myAnswers) { answer ->
                                Column {
                                    Text(text = "Question Title: ${answer.title}")
                                    Text(text = "My Answer: ${answer.answer}")
                                }
                            }
                        }
                    }
                    2 -> {
                        LazyColumn(modifier = Modifier.padding(16.dp)) {
                            items(state.myQuestions) { question ->
                                Column {
                                    Text(text = "Question Title: ${question.title}")
                                    Text(text = "Likes: ${question.like}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
