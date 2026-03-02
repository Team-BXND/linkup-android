package com.linkup.android.feature.qna

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.linkup.android.root.NavGroup
import com.linkup.android.ui.components.Popular

@Composable
fun UsefulPostList(
    navController: NavController
) {
    val viewModel: QnAViewModel = hiltViewModel()

    val top3 by viewModel.top3Hot.collectAsState()

    var selectedIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.loadTop3Hot()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(top3) { index, item ->
            Popular (
                rank = index + 1,
                title = item.title,
                commentCount = item.commentCount,
                like = item.like,
                isSelected = selectedIndex == index,
                onClick = {
                    navController.navigate("detail/${item.id}")
                }
            )
        }
    }
}