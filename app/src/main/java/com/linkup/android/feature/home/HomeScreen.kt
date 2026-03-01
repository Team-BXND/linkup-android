package com.linkup.android.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.linkup.android.ui.components.HomeQna
import com.linkup.android.ui.components.ImagePager
import com.linkup.android.ui.components.TopBar

@Composable
fun HomeScreen(
    navController: NavController,
    innerPadding: PaddingValues
) {

    val viewModel: HomeViewModel = hiltViewModel()

    val top5 by viewModel.top5Hot.collectAsState()

    var selectedIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.loadTop5Hot()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(innerPadding)
            .padding(horizontal = 13.dp)
    ) {
        TopBar(navController)

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 24.dp)
                .padding(horizontal = 20.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp)),
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
                    .weight(1f)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(Color.White, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            ) {
                Text(
                    "🔥 지금 뜨거운 Q&A",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    itemsIndexed(top5) { index, item ->
                        HomeQna(
                            rank = index + 1,
                            title = item.title,
                            author = item.author,
                            like = item.like,
                            isSelected = selectedIndex == index,
                            onClick = {
                                selectedIndex = index
                                navController.navigate("detail/${item.id}")
                            }
                        )
                    }
                }

            }
        }
    }
}
