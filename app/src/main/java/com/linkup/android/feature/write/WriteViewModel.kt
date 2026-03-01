package com.linkup.android.feature.write

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkup.android.feature.write.UploadState
import com.linkup.android.network.Category
import com.linkup.android.network.ErrorParser
import com.linkup.android.network.file.UploadRequest
import com.linkup.android.network.file.UploadService
import com.linkup.android.network.post.PatchRequest
import com.linkup.android.network.post.PostRequest
import com.linkup.android.network.post.PostResponse
import com.linkup.android.network.post.PostService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject


@HiltViewModel
class WriteViewModel @Inject constructor(
    private val postService: PostService,
    private val uploadService: UploadService
) : ViewModel() {

    var uploadState = mutableStateOf(UploadState())
    private set

    var uiState by mutableStateOf(WriteUiState())
        private set

    fun post(
        selectedCategory: Category,
        title: String,
        content: String,
        author: String,
    ) {
        viewModelScope.launch {
            try {
                val result = postService.createPost(
                    PostRequest(
                        category = selectedCategory,
                        title = title,
                        content = content,
                        author = author
                    )
                )
                if (result.isSuccessful) {
                    uiState = uiState.copy(isSuccess = true)
                } else {
                    val error = ErrorParser.parse(result, PostResponse::class.java)
                    uiState = when (error?.code) {
                        "EMAIL_ALREADY_USED" -> {
                            uiState.copy(globalError = "이미 사용 중인 이메일입니다.")
                        }

                        else -> {
                            uiState.copy(globalError = "글 등록")
                        }
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    fun uploadFile(context: Context, uri: Uri) {
        viewModelScope.launch {
            uploadState.value = UploadState(isUploading = true)

            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                    ?: throw Exception("파일을 열 수 없습니다")

                val bytes = inputStream.readBytes()

                val part = MultipartBody.Part.createFormData(
                    "file",
                    "image.png",
                    bytes.toRequestBody("image/*".toMediaType())
                )

                val response = uploadService.uploadFile(part)

                if (!response.isSuccessful)
                    throw Exception("업로드 실패: ${response.code()}")

                val s3Key = response.body()?.data
                    ?: throw Exception("s3Key를 가져올 수 없습니다")

                uploadState.value = UploadState(
                    isUploading = false,
                    s3Key = s3Key
                )

            } catch (e: Exception) {
                uploadState.value = UploadState(
                    isUploading = false,
                    error = e.message
                )
            }
        }
    }

    suspend fun convertS3KeysToTempUrls(content: String): String {

        val regex = Regex("""!\[\]\(([^)]+)\)""")
        var updatedContent = content

        regex.findAll(content).forEach { match ->

            val value = match.groupValues[1]

            // 이미 http로 시작하면 tempUrl로 판단 → 스킵
            if (value.startsWith("http")) return@forEach

            try {
                val response = uploadService.getTempUrl(value)
                if (!response.isSuccessful) return@forEach

                val tempUrl = response.body()?.data ?: return@forEach

                updatedContent = updatedContent.replace(
                    "![]($value)",
                    "![]($tempUrl)"
                )

            } catch (_: Exception) { }
        }

        return updatedContent
    }


    fun updatePost(
        postId: Int,
        selectedCategory: Category,
        title: String,
        content: String,
        author: String
    ) {
        viewModelScope.launch {
            try {
                val request = PatchRequest(
                    category = selectedCategory,
                    title = title,
                    content = content,
                    author = author
                )

                val response = postService.updatePost(postId, request)

                if (response.isSuccessful) {
                    uiState = uiState.copy(isSuccess = true)
                } else {
                    val error = ErrorParser.parse(response, PostResponse::class.java)
                    uiState = uiState.copy(globalError = error?.message ?: "수정 실패")
                }

            } catch (e: Exception) {
                uiState = uiState.copy(globalError = e.message ?: "수정 중 오류 발생")
            }
        }
    }
}