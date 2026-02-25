package com.linkup.android.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.linkup.android.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagePager(
    modifier: Modifier = Modifier
) {

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

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
    ) { page ->

        Image(
            painter = painterResource(id = images[page]),
            contentDescription = descriptions[page],
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
}