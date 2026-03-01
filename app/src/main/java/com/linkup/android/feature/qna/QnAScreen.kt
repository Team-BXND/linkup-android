package com.linkup.android.feature.qna

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.linkup.android.feature.home.HomeViewModel
import com.linkup.android.network.Category
import com.linkup.android.root.NavGroup
import com.linkup.android.ui.components.ImagePager
import com.linkup.android.ui.components.ToggleButton
import com.linkup.android.ui.components.TopBar
import com.linkup.android.ui.theme.MainColor

enum class BoardTab {
    USEFUL_POST,
    QUESTION
}

@Composable
fun QnaScreen(
    navController: NavController,
    innerPadding: PaddingValues,
    initialCategory: Category = Category.ALL,
) {

    val viewModel: HomeViewModel = hiltViewModel()

    var selectedTab by remember(initialCategory) {
        mutableStateOf(
            if (initialCategory == Category.ALL)
                BoardTab.USEFUL_POST
            else
                BoardTab.QUESTION
        )
    }

    LaunchedEffect(Unit) {
        viewModel.loadTop5Hot()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(innerPadding)
            .padding(horizontal = 13.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopBar(navController)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 24.dp)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    "💬대소고에서 궁금한 점이 있다면?",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                )

                ImagePager(
                    navController,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Column(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp,16.dp))
                        .background(Color.White, shape = RoundedCornerShape(16.dp,16.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {

                        ToggleButton(
                            text = "\uD83D\uDD25 가장 유용했던 글",
                            selected = selectedTab == BoardTab.USEFUL_POST,
                            onClick = { selectedTab = BoardTab.USEFUL_POST },
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        ToggleButton(
                            text = "\uD83D\uDE4B 질문 목록",
                            selected = selectedTab == BoardTab.QUESTION,
                            onClick = { selectedTab = BoardTab.QUESTION },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    when (selectedTab) {
                        BoardTab.USEFUL_POST -> UsefulPostList(navController)
                        BoardTab.QUESTION -> QuestionList(navController,initialCategory)
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = {
                navController.navigate(NavGroup.WRITE)
            },
            containerColor = MainColor,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "글쓰기",
                tint = Color.White
            )
        }
    }
}