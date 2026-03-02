package com.linkup.android.feature.qna

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.linkup.android.network.Category
import com.linkup.android.ui.components.Question
import com.linkup.android.ui.theme.MainColor


@Composable
fun QuestionList(
    navController: NavController,
    initialCategory: Category
) {

    val viewModel: QnAViewModel = hiltViewModel()
    val pagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    LaunchedEffect(initialCategory) {
        viewModel.changeCategory(initialCategory)
    }

    Column {
        TabRow(
            selectedTabIndex = Category.entries.indexOf(selectedCategory),
            containerColor = Color.White,
            indicator = {},
            divider = {}

        ) {
            Category.entries.forEachIndexed { index, category ->
                val selected = selectedCategory == category
                Tab(
                    selected = selected,
                    onClick = { viewModel.changeCategory(category) },
                    modifier = Modifier
                        .padding(2.dp)
                        .background(
                            if (selected) MainColor else Color.Gray, RoundedCornerShape(40.dp)
                        ),
                    text = {
                        Text(
                            text = category.label,
                            color = Color.White
                        )
                    }
                )

            }
        }

        LazyColumn {

            items(pagingItems.itemCount) { index ->
                val item = pagingItems[index]

                item?.let {
                    Question(
                        title = it.title,
                        commentCount = it.commentCount,
                        like = it.like,
                        isSelected = false,
                        onClick = {
                            navController.navigate("detail/${it.id}")
                        }
                    )
                }
            }

            when (pagingItems.loadState.append) {
                is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }

                is LoadState.Error -> {
                    item { Text("에러 발생") }
                }

                else -> {}
            }
        }
    }
}