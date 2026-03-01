package com.linkup.android.feature.write

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.linkup.android.network.Category
import com.linkup.android.root.NavGroup
import com.linkup.android.ui.components.CategoryDropdown
import com.linkup.android.ui.components.CustomButton
import com.linkup.android.ui.components.CustomTextField
import com.linkup.android.ui.components.CustomTextValue
import com.linkup.android.ui.components.TopBar
import com.linkup.android.ui.theme.MainColor
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun WriteScreen(
    navController: NavController,
    innerPadding: PaddingValues,
    postId: Int? = null,
    initialTitle: String? = null,
    initialContent: String? = null,
    initialCategory: Category? = null,
    initialAuthor: String? = null
) {
    val viewModel: WriteViewModel = hiltViewModel()

    var title by remember { mutableStateOf(initialTitle ?: "") }
    var content by remember { mutableStateOf(TextFieldValue(initialContent ?: "")) }
    var selectedCategory by remember { mutableStateOf(initialCategory) }
    var nickname by remember { mutableStateOf(initialAuthor ?: "") }
    var isPreview by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val uiState = viewModel.uiState
    val uploadState = viewModel.uploadState.value


    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navController.navigate(NavGroup.HOME) {
                popUpTo(0)
            }
        }
    }

    LaunchedEffect(uploadState.s3Key) {
        val key = uploadState.s3Key
        if (!key.isNullOrBlank()) {
            content = TextFieldValue(
                content.text + "\n![]($key)\n"
            )
        }
    }



    LaunchedEffect(uploadState) {
        when {
            uploadState.isUploading -> Log.d("UPLOAD", "이미지 업로드 중...")
            uploadState.error != null -> Log.d("UPLOAD", "업로드 실패: ${uploadState.error}")
            uploadState.uploadedUrl != null -> Log.d("UPLOAD", "업로드 완료! URL: ${uploadState.uploadedUrl}")
        }
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
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 24.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))
                .background(Color.White, shape = RoundedCornerShape(16.dp))
        ) {
            // 제목 + Q 표시
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Q",
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainColor,
                    textAlign = TextAlign.Center,
                )
                CustomTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeHolder = "제목을 입력하세요."
                )
            }

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                // 닉네임 / 카테고리
                CustomTextField(
                    value = nickname,
                    onValueChange = { nickname = it },
                    placeHolder = "닉네임을 입력하세요."
                )
                CategoryDropdown(
                    selected = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (isPreview) "편집 모드로 전환" else "미리보기 모드로 전환",
                        color = MainColor,
                        modifier = Modifier
                            .clickable { isPreview = !isPreview }
                            .padding(8.dp)
                    )
                }


                if (!isPreview) {
                    CustomTextValue(
                        value = content,
                        onValueChange = { content = it },
                        placeHolder = "본문을 입력하세요",
                        modifier = Modifier.weight(1f)
                    )

                    EditorToolbar(
                        content = content,
                        onContentChange = { content = it },
                        uploadImageToS3 = { uri -> viewModel.uploadFile(context, uri) },
                        uploadedUrl = viewModel.uploadState.value.uploadedUrl,
                    )

                } else {
                    MarkdownText(
                        markdown = content.text,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .weight(1f),
                        fontSize = 14.sp
                    )
                }

                CustomButton(
                    text = if (postId == null) "질문하기" else "수정 완료",
                    containerColor = MainColor,
                    contentColor = Color.White,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                        .width(120.dp)
                        .align(Alignment.End)
                    ,
                    fontSize = 14.sp,
                    onClick = {
                        if (selectedCategory != null) {
                            if (postId == null) {
                                viewModel.post(
                                    selectedCategory = selectedCategory!!,
                                    title = title,
                                    author = nickname,
                                    content = content.text
                                )
                            } else {
                                viewModel.updatePost(
                                    postId = postId,
                                    selectedCategory = selectedCategory!!,
                                    author = nickname,
                                    title = title,
                                    content = content.text
                                )
                            }
                        }
                    },
                    border = MainColor
                )
            }
        }
    }
}