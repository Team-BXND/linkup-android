package com.linkup.android.feature.post

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.linkup.android.feature.auth.signup.SignUpUiState
import com.linkup.android.network.ErrorParser
import com.linkup.android.network.auth.signUp.SignUpResponse
import com.linkup.android.network.file.UploadRequest
import com.linkup.android.network.file.UploadService
import com.linkup.android.network.post.AcceptRequest
import com.linkup.android.network.post.AnswerRequest
import com.linkup.android.network.post.PostService
import com.linkup.android.network.post.PostDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject


sealed class WriteCommentState {
    object Idle : WriteCommentState()
    object Loading : WriteCommentState()
    data class Success(val message: String) : WriteCommentState()
    data class Error(val message: String) : WriteCommentState()
}

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val postService: PostService,
    private val uploadService: UploadService
) : ViewModel() {

    var post by mutableStateOf<PostDetail?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var uiState by mutableStateOf(PostDetailUiState())
        private set

    private val _writeState = MutableStateFlow<WriteCommentState>(WriteCommentState.Idle)
    val writeState: StateFlow<WriteCommentState> = _writeState

    fun loadPostDetail(id: Int) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = postService.getPostDetail(id)
                post = response.data
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    fun writeComment(postId: Int, content: String) {
        if (content.isBlank()) return

        viewModelScope.launch {
            _writeState.value = WriteCommentState.Loading

            try {
                val response = postService.writeComment(
                    request = AnswerRequest(content = content),
                    id = postId
                )

                _writeState.value = WriteCommentState.Success(
                    message = response.body()?.data?.message ?: "답글이 등록되었습니다."
                )

                loadPostDetail(postId)

            } catch (e: Exception) {
                _writeState.value = WriteCommentState.Error(
                    message = e.message ?: "답글 작성 실패"
                )
            }
        }
    }

    fun like(
        postId: Int
    ) {
        viewModelScope.launch {
            try {
                val result = postService.like(postId)
                if (result.isSuccessful) {
                    uiState = uiState.copy(isSuccess = true)
                } else {
                    val error = ErrorParser.parse(result, SignUpResponse::class.java)
                    uiState = uiState.copy(globalError = error?.message ?: "좋아요 실패")
                }
            } catch (e: Exception) {
                uiState = uiState.copy(globalError = e.message ?: "좋아요 중 오류 발생")
            } finally {
                loadPostDetail(postId)
            }
        }
    }

    fun acceptComment(postId: Int, commentId: Int) {
        viewModelScope.launch {
            try {
                val request = AcceptRequest(commentId)
                val result = postService.acceptComment(
                    id = postId,
                    ansid = commentId,
                    request = request
                )

                if (result.isSuccessful) {
                    loadPostDetail(postId)
                } else {
                    val error = ErrorParser.parse(result, SignUpResponse::class.java)
                    uiState = uiState.copy(globalError = error?.message ?: "채택 실패")
                }
            } catch (e: Exception) {
                uiState = uiState.copy(globalError = e.message ?: "채택 중 오류 발생")
            }
        }
    }

    fun deletePost(postId: Int) {
        viewModelScope.launch {
            try {
                val response = postService.deletePost(postId)
                if (response.isSuccessful) {
                    uiState = uiState.copy(deleteSuccess = true)
                } else {
                    val error = ErrorParser.parse(response, SignUpResponse::class.java)
                    errorMessage = error?.message ?: "삭제 실패"
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "삭제 중 오류 발생"
            }
        }
    }

    fun resetState() {
        _writeState.value = WriteCommentState.Idle
    }

    suspend fun convertS3KeysToTempUrls(content: String): String {

        val regex = Regex("""!\[\]\(([^)]+)\)""")
        var updatedContent = content

        regex.findAll(content).forEach { match ->
            val s3Key = match.groupValues[1]

            // 이미 http면 스킵
            if (s3Key.startsWith("http")) return@forEach

            try {
                val response = uploadService.getTempUrl(s3Key)
                if (response.isSuccessful) {
                    val tempUrl = response.body()?.data ?: return@forEach

                    updatedContent = updatedContent.replace(
                        "![]($s3Key)",
                        "![]($tempUrl)"
                    )
                }
            } catch (_: Exception) { }
        }

        return updatedContent
    }

}
