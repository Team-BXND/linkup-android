package com.linkup.android.feature.post

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.linkup.android.root.NavGroup
import com.linkup.android.ui.components.CustomButton
import com.linkup.android.ui.components.CustomTextField
import com.linkup.android.ui.components.SendButton
import com.linkup.android.ui.components.ThumbUp
import com.linkup.android.ui.components.TopBar
import com.linkup.android.ui.theme.MainColor
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun PostDetailScreen(
    navController: NavController,
    innerPadding: PaddingValues,
    postId: Int
) {

    val viewModel: PostDetailViewModel = hiltViewModel()
    val post = viewModel.post
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage
    var comment by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val writeState by viewModel.writeState.collectAsState()
    var renderedContent by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(postId) {
        viewModel.loadPostDetail(postId)
    }

    LaunchedEffect(writeState) {
        if (writeState is WriteCommentState.Success) {
            comment = ""
            viewModel.resetState()
        }
    }

    LaunchedEffect(viewModel.uiState.deleteSuccess) {
        if (viewModel.uiState.deleteSuccess) {
            navController.navigate(NavGroup.HOME) {
                popUpTo(0)
            }
        }
    }

    LaunchedEffect(post) {
        post?.let {
            renderedContent = viewModel.convertS3KeysToTempUrls(it.content)
        }
    }

    if (isLoading) {
        Text("로딩 중...", modifier = Modifier.padding(16.dp))
    } else if (errorMessage != null) {
        Text("오류: $errorMessage", modifier = Modifier.padding(16.dp), color = Color.Red)
    } else if (post != null) {
            val canAccept = post.isAuthor && post.comment.none { it.isAccepted }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 13.dp)
                .background(Color.White)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TopBar(navController)

            Column(
                modifier = Modifier
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
                    .background(Color.White, shape = RoundedCornerShape(16.dp)),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Q",
                            fontSize = 50.sp,
                            fontWeight = FontWeight.Bold,
                            color = MainColor,
                            textAlign = TextAlign.Center,
                        )

                        Text(
                            text = post.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1B1B1B),
                            modifier = Modifier
                                .weight(1f)
                        )

                        ThumbUp(
                            backgroundColor = MainColor,
                            mainColor = Color.White,
                            onClick = {
                                viewModel.like(postId)
                            }
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            post.author,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                        Text(
                            post.category.label,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                        Text(
                            text = "작성일 : ${post.createdAt}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                        Text(
                            "유용해요 : ${post.like}개",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                    }

                    MarkdownText(
                        markdown = renderedContent ?: "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        fontSize = 12.sp,
                        color = Color.Black
                    )

                    Row(
                        modifier = Modifier
                            .padding(vertical = 16.dp, horizontal = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 16.dp, horizontal = 16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            if (post.isAuthor) {
                                CustomButton(
                                    text = "수정",
                                    containerColor = MainColor,
                                    contentColor = Color.White,
                                    border = MainColor,
                                    modifier = Modifier
                                        .width(82.dp),
                                    fontSize = 16.sp,
                                    onClick = {
                                        post.let {
                                            val route = "write/$postId" +
                                                    "?title=${Uri.encode(it.title)}" +
                                                    "&content=${Uri.encode(it.content)}" +
                                                    "&category=${it.category.name}" +
                                                    "&author=${Uri.encode(it.author)}"
                                            navController.navigate(route)
                                        }
                                    }                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                CustomButton(
                                    text = "삭제",
                                    containerColor = MainColor,
                                    contentColor = Color.White,
                                    border = MainColor,
                                    modifier = Modifier
                                        .width(82.dp),
                                    fontSize = 16.sp,
                                    onClick = {
                                        viewModel.deletePost(postId)
                                    }                                )
                            }
                        }
                    }

                }
            }

            Column {
                Text(
                    "${post.comment.size}개의 답변",
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp
                )

                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    CustomTextField(
                        value = comment,
                        onValueChange = { comment = it },
                        placeHolder = "답변을 입력해주세요.",
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    SendButton(
                        backgroundColor = MainColor,
                        mainColor = Color.White,
                        onClick = {
                            Log.d("POST", "click")
                            viewModel.writeComment(postId, comment)
                        }
                    )
                }
            }

            post.comment.forEach { c ->
                Column(
                    modifier = Modifier
                        .shadow(4.dp, RoundedCornerShape(16.dp))
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .then(
                            if (c.isAccepted) {
                                Modifier.border(
                                    width = 2.dp,
                                    color = MainColor,
                                    shape = RoundedCornerShape(16.dp)
                                )
                            } else Modifier
                        )
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("A", fontSize = 50.sp, fontWeight = FontWeight.Bold, color = Color.Red)
                        Text(
                            "${c.author}님의 답변",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "작성일 : ${c.createdAt}",
                            fontSize = 10.sp,
                            color = Color.Gray
                        )

                        // 글 작성자가 댓글을 채택할 수 있는 경우만 버튼 표시
                        if (canAccept && !c.isAccepted) {
                            CustomButton(
                                text = "채택하기",
                                containerColor = MainColor,
                                contentColor = Color.White,
                                modifier = Modifier.width(100.dp),
                                fontSize = 12.sp,
                                onClick = { viewModel.acceptComment(postId, c.commentId) },
                                border = MainColor
                            )
                        }
                    }

                    Text(c.content, fontSize = 12.sp, lineHeight = 20.sp)
                }
            }
        }
    }
}