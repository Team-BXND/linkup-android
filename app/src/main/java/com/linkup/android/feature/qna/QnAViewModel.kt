package com.linkup.android.feature.qna

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.linkup.android.network.Category
import com.linkup.android.network.qna.PopularPagingSource
import com.linkup.android.network.qna.QnaItemResponse
import com.linkup.android.network.qna.QnaService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.orEmpty
import kotlin.collections.take

@HiltViewModel
class QnAViewModel @Inject constructor(
    private val qnaService: QnaService
) : ViewModel() {

    private val _top3Hot = MutableStateFlow<List<QnaItemResponse>>(emptyList())
    val top3Hot: StateFlow<List<QnaItemResponse>> = _top3Hot


    private val _selectedCategory = MutableStateFlow(Category.ALL)
    val selectedCategory = _selectedCategory.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingFlow = _selectedCategory.flatMapLatest { category ->

        Pager(
            config = PagingConfig(
                pageSize = 5,
                initialLoadSize = 5,
                enablePlaceholders = false
            )
        ) {
            PopularPagingSource(
                service = qnaService,
                category = category
            )
        }.flow

    }.cachedIn(viewModelScope)

    fun changeCategory(category: Category) {
        _selectedCategory.value = category
    }

    fun loadTop3Hot() {
        viewModelScope.launch {
            try {
                val response = qnaService.hot(page = 0)

                if (response.isSuccessful) {
                    val data = response.body()?.data.orEmpty()
                    _top3Hot.value = data.take(3)
                }

            } catch (e: Exception) {
                Log.e("API_TEST", "exception = ${e.message}")
            }
        }
    }
}