package com.linkup.android.feature.rank

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RankScreen(viewModel: RankViewModel = hiltViewModel()) {
    val state = viewModel.state.value

    LaunchedEffect(Unit) {
        viewModel.getRank()
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
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(state.ranking) { userRank ->
                    Column {
                        Text(text = "Rank: ${userRank.rank}")
                        Text(text = "Username: ${userRank.username}")
                        Text(text = "Points: ${userRank.point}")
                    }
                }
            }
        }
    }
}