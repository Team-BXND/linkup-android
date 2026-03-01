package com.linkup.android.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val _top5Hot = MutableStateFlow<List<QnaItemResponse>>(emptyList())
    val top5Hot: StateFlow<List<QnaItemResponse>> = _top5Hot

    fun loadTop5Hot() {
        viewModelScope.launch {
            try {
                val response = qnaService.hot(page = 0)

                Log.d("API_TEST", "code = ${response.code()}")
                Log.d("API_TEST", "body = ${response.body()}")

                if (response.isSuccessful) {
                    val data = response.body()?.data.orEmpty()
                    Log.d("API_TEST", "data size = ${data.size}")

                    _top5Hot.value = data.take(5)
                } else {
                    Log.d("API_TEST", "error body = ${response.errorBody()?.string()}")
                }

            } catch (e: Exception) {
                Log.e("API_TEST", "exception = ${e.message}")
            }
        }
    }
}