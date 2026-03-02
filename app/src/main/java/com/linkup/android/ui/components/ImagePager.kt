package com.linkup.android.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.linkup.android.R
import com.linkup.android.network.Category
import com.linkup.android.root.NavGroup
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagePager(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val categories = listOf(
        Category.CODE,
        Category.SCHOOL,
        Category.PROJECT
    )

    val images = listOf(
        R.drawable.code,
        R.drawable.school,
        R.drawable.project
    )

    val descriptions = listOf(
        "코드 게시판",
        "학교 생활 게시판",
        "프로젝트 게시판"
    )

    val pagerState = rememberPagerState(
        pageCount = { images.size }
    )

    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)

            val nextPage = (pagerState.currentPage + 1) % images.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
    ) { page ->

        Image(
            painter = painterResource(id = images[page]),
            contentDescription = descriptions[page],
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(
                        NavGroup.createQnaRoute(categories[page].name)
                    )
                },
            contentScale = ContentScale.Crop
        )
    }
}