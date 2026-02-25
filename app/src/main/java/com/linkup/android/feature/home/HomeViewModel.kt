package com.linkup.android.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkup.android.network.qna.Category
import com.linkup.android.network.qna.QnaItemResponse
import com.linkup.android.network.qna.QnaService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val qnaService: QnaService
) : ViewModel() {
    private val _top3Hot = MutableStateFlow<List<QnaItemResponse>>(emptyList())
    val top5Hot: StateFlow<List<QnaItemResponse>> = _top3Hot

//    fun loadTop3Hot() {
//        viewModelScope.launch {
//
//            val fakeList = listOf(
//                QnaItemResponse(
//                    id = 1,
//                    title = "Compose recomposition 질문",
//                    author = "홍길동",
//                    like = 12,
//                    category = Category.ALL,
//                    preview = "String",
//                    isAccepted = true,
//                    commentCount = 1,
//                    createdAt = "String"
//                ),
//                QnaItemResponse(
//                    id = 2,
//                    title = "Hilt 주입이 안됩니다",
//                    author = "김철수",
//                    like = 9,
//                    category = Category.ALL,
//                    preview = "String",
//                    isAccepted = true,
//                    commentCount = 1,
//                    createdAt = "String"
//                ),
//                QnaItemResponse(
//                    id = 3,
//                    title = "Coroutine scope 차이",
//                    author = "이영희",
//                    like = 7,
//                    category = Category.ALL,
//                    preview = "String",
//                    isAccepted = true,
//                    commentCount = 1,
//                    createdAt = "String"
//                ),
//                QnaItemResponse(
//                    id = 4,
//                    title = "StateFlow vs LiveData",
//                    author = "박민수",
//                    like = 5,
//                    category = Category.ALL,
//                    preview = "String",
//                    isAccepted = true,
//                    commentCount = 1,
//                    createdAt = "String"
//                ),
//                QnaItemResponse(
//                    id = 5,
//                    title = "Navigation backstack 문제",
//                    author = "최지우",
//                    like = 3,
//                    category = Category.ALL,
//                    preview = "String",
//                    isAccepted = true,
//                    commentCount = 1,
//                    createdAt = "String"
//                )
//            )
//
//            _top3Hot.value = fakeList
//        }
//    }
    fun loadTop3Hot() {
        viewModelScope.launch {
            try {
                val response = qnaService.hot(page = 0)

                Log.d("API_TEST", "code = ${response.code()}")
                Log.d("API_TEST", "body = ${response.body()}")

                if (response.isSuccessful) {
                    val data = response.body()?.data.orEmpty()
                    Log.d("API_TEST", "data size = ${data.size}")

                    _top3Hot.value = data.take(5)
                } else {
                    Log.d("API_TEST", "error body = ${response.errorBody()?.string()}")
                }

            } catch (e: Exception) {
                Log.e("API_TEST", "exception = ${e.message}")
            }
        }
    }
}